package org.ipni.indexer.loader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.annotation.PostConstruct;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.util.ContentStreamBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tukaani.xz.XZInputStream;

import com.google.common.collect.ImmutableList;

@Component
public class Loader implements Runnable {

	private static final ModifiableSolrParams params = new ModifiableSolrParams()
			.add("commit", "true")
			.add("header", "true")
			.add("separator", "|")
			.add("f.lookup_basionym_id.split", "true")
			.add("f.lookup_basionym_id.separator", ",")
			.add("f.lookup_hybrid_parent_id.split", "true")
			.add("f.lookup_hybrid_parent_id.separator", ",")
			.add("f.lookup_later_homonym_of_id.split", "true")
			.add("f.lookup_later_homonym_of_id.separator", ",")
			.add("f.lookup_nomenclatural_synonym_id.split", "true")
			.add("f.lookup_nomenclatural_synonym_id.separator", ",")
			.add("f.lookup_same_citation_as_id.split", "true")
			.add("f.lookup_same_citation_as_id.separator", ",")
			.add("f.lookup_superfluous_name_of_id.split", "true")
			.add("f.lookup_superfluous_name_of_id.separator", ",")
			.add("f.lookup_validation_of_id.split", "true")
			.add("f.lookup_validation_of_id.separator", ",")
			.add("f.lookup_parent_id.split", "true")
			.add("f.lookup_parent_id.separator", ",")
			.add("f.lookup_orthographic_variant_of_id.split", "true")
			.add("f.lookup_orthographic_variant_of_id.separator", ",")
			.add("f.detail_author_iso_countries.split","true")
			.add("f.detail_author_iso_countries.separator","@")
			.add("f.detail_taxon_groups_flat.split", "true")
			.add("f.detail_taxon_groups_flat.separator", ",")
			.add("f.detail_alternative_names.split", "true")
			.add("f.detail_alternative_names.separator", ";")
			.add("f.detail_author_team_ids.split", "true")
			.add("f.detail_author_team_ids.separator", "$")
			.add("f.detail_species_author_team_ids.split", "true")
			.add("f.detail_species_author_team_ids.separator", "$");

	private static final List<String> suggesters = ImmutableList.<String>of("scientific-name", "author", "publication");

	private SolrClient adminClient;
	private SolrClient buildClient;
	private Logger logger = LoggerFactory.getLogger(Loader.class);

	@Value("${ipni.flat}")
	private String NAMES_FILE_URL;

	@Value("${ipni.authors}")
	private String AUTHORS_FILE_URL;

	@Value("${ipni.publications}")
	private String PUBLICATIONS_FILE_URL;

	@Value("${solr.core1}")
	private String LIVE_CORE;

	@Value("${solr.core2}")
	private String BUILD_CORE;

	@Value("${solr.url}")
	private String SOLR_SERVER;

	@Value("${ipni.stats.names_published}")
	private String STATS_NAMES_PUBLISHED;

	@Value("${ipni.stats.record_activity}")
	private String STATS_RECORD_ACTIVITY;

	@PostConstruct
	public void initializeSolrClients() {
		adminClient = new HttpSolrClient.Builder(SOLR_SERVER).build();
		buildClient = new HttpSolrClient.Builder(SOLR_SERVER + BUILD_CORE).build();
	}

	@Override
	public void run() {
		try {
			File namesFile = getFile(NAMES_FILE_URL);
			File authorsFile = getFile(AUTHORS_FILE_URL);
			File publicationsFile = getFile(PUBLICATIONS_FILE_URL);
			File statsPublishedFile = getFile(STATS_NAMES_PUBLISHED);
			File statsActivityFile = getFile(STATS_RECORD_ACTIVITY);

			loadData(namesFile);
			loadData(authorsFile);
			loadData(publicationsFile);
			loadStats(statsPublishedFile, statsActivityFile);

			updateSuggesters();
			optimizeCore();
			switchCores();
			clearCore();

			namesFile.delete();
			authorsFile.delete();
			publicationsFile.delete();
			statsPublishedFile.delete();
			statsActivityFile.delete();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}

	private void loadStats(File statsPublishedFile, File statsActivityFile) throws SolrServerException, IOException {
		StatsLoader loader = new StatsLoader(statsPublishedFile, statsActivityFile);
		logger.info("Loading stats data");
		buildClient.add(loader.getNamesPublishedStats());
		buildClient.add(loader.getRecordActivityStats());
		logger.info("Commiting stats data");
		buildClient.commit();
	}

	private void optimizeCore() {
		try {
			logger.info("Optimizing {}", BUILD_CORE);
			buildClient.optimize();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}

	private void updateSuggesters() {
		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/suggest");
		query.setParam("suggest.build", "true");
		query.setParam("suggest", "true");

		for(String suggester : suggesters) {
			query.setParam("suggest.dictionary", suggester);

			try {
				logger.info("Building {} suggester", suggester);
				buildClient.query(query);
			} catch (SolrServerException | IOException e) {
				logger.error("Error building {} suggester: {}", suggester, e.getMessage());
			}
		}
	}

	public File getFile(String filePathUrl) throws MalformedURLException, IOException {
		File file = File.createTempFile(filePathUrl, null);

		try(BufferedInputStream in = new BufferedInputStream(new URL(filePathUrl).openStream());
				XZInputStream unzip = new XZInputStream(in);
				FileOutputStream out = new FileOutputStream(file)) {
			final byte[] buffer = new byte[8192];
			int n = 0;
			while ((n = unzip.read(buffer)) != -1) {
				out.write(buffer, 0, n);
			}
		}

		return file;
	}

	public void loadData(File indexFile) throws MalformedURLException, IOException, SolrServerException {
		logger.info("Loading {} into {}...", indexFile.getName(), BUILD_CORE);
		ContentStreamBase.FileStream stream = new ContentStreamBase.FileStream(indexFile);
		stream.setContentType("application/csv");
		ContentStreamUpdateRequest request = new ContentStreamUpdateRequest("/" + BUILD_CORE + "/update");
		request.setParams(params);
		request.addContentStream(stream);
		adminClient.request(request);
		logger.info("{} loaded", indexFile.getName());
	}

	public void switchCores() throws SolrServerException, IOException {
		logger.info("Swapping cores..");
		CoreAdminRequest.swapCore(LIVE_CORE, BUILD_CORE, adminClient);
		logger.info("Cores swapped, live core is now {}", LIVE_CORE);
	}

	public void clearCore() throws SolrServerException, IOException {
		logger.info("Clearing old core");
		buildClient.deleteByQuery("*:*");
		buildClient.commit();
		logger.info("Old core cleared");
	}
}
