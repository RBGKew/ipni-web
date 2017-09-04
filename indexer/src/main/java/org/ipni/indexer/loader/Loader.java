package org.ipni.indexer.loader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.util.ContentStreamBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tukaani.xz.XZInputStream;

import com.google.common.collect.ImmutableList;

@Component
public class Loader {

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
			.add("f.detail_alternative_names.separator", ",")
			.add("f.detail_author_team_ids.split", "true")
			.add("f.detail_author_team_ids.separator", "$")
			.add("f.detail_species_author_team_ids.split", "true")
			.add("f.detail_species_author_team_ids.separator", "$");
	

	private static final List<String> suggesters = ImmutableList.<String>of("scientific-name", "author", "publication");

	private SolrClient adminClient;
	private SolrClient buildClient;
	private Logger logger = LoggerFactory.getLogger(Loader.class);

	private File namesFile = new File("/indexNames.csv");
	private File authorsFile = new File("/indexAuthors.csv");
	private File publicationsFile = new File("/indexPublications.csv");
	private File updateFile = new File("/update.csv");

	@Value("${ipni.flat}")
	private String NAMES_FILE_URL;

	@Value("${ipni.authors}")
	private String AUTHORS_FILE_URL;

	@Value("${ipni.publications}")
	private String PUBLICATIONS_FILE_URL;

	@Value("${powo.ids}")
	private String UPDATE_FILE_URL;

	@Value("${solr.core1}")
	private String LIVE_CORE;

	@Value("${solr.core2}")
	private String BUILD_CORE;

	@Value("${solr.url}")
	private String SOLR_SERVER;

	@PostConstruct
	public void initializeSolrClients() {
		adminClient = new HttpSolrClient.Builder(SOLR_SERVER).build();
		buildClient = new HttpSolrClient.Builder(SOLR_SERVER + BUILD_CORE).build();
	}

	public void load() {
		try {
			getFile(namesFile, NAMES_FILE_URL);
			loadData(namesFile);
			getFile(authorsFile, AUTHORS_FILE_URL);
			loadData(authorsFile);
			getFile(publicationsFile, PUBLICATIONS_FILE_URL);
			loadData(publicationsFile);
			getFile(updateFile, UPDATE_FILE_URL);
			addPOWOUsage();
			updateSuggesters();
			optimizeCore();
			switchCores();
			clearCore();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
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

	public void getFile(File file, String filePathUrl) throws MalformedURLException, IOException {
		logger.info("Getting {}..", filePathUrl);

		if (file.exists()) {
			file.delete();
		}

		try(BufferedInputStream in = new BufferedInputStream(new URL(filePathUrl).openStream());
				XZInputStream unzip = new XZInputStream(in);
				FileOutputStream out = new FileOutputStream(file)) {
			final byte[] buffer = new byte[8192];
			int n = 0;
			while ((n = unzip.read(buffer)) != -1) {
				out.write(buffer, 0, n);
			}
		}
	}

	public void loadData(File indexFile) throws MalformedURLException, IOException, SolrServerException {
		logger.info("Loading Data into {}...", BUILD_CORE);
		ContentStreamBase.FileStream stream = new ContentStreamBase.FileStream(indexFile);
		stream.setContentType("application/csv");
		ContentStreamUpdateRequest request = new ContentStreamUpdateRequest("/" + BUILD_CORE + "/update");
		request.setParams(params);
		request.addContentStream(stream);
		adminClient.request(request);
		logger.info("Data loaded");
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

	public void addPOWOUsage() throws MalformedURLException, IOException, SolrServerException {
		logger.info("Updating Solr Data...");

		String IPNI_URI = "urn:lsid:ipni.org:names:";
		String currentLine;
		String id;

		Collection<SolrInputDocument> solrDocs = new ArrayList<>();

		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(updateFile), "UTF8"))) {
			while ((currentLine = br.readLine()) != null) {
				id = currentLine.replaceFirst(IPNI_URI, "");
				SolrInputDocument sdoc = new SolrInputDocument();
				sdoc.addField("id", id);
				sdoc.addField("powo_b", Collections.singletonMap("set", true));
				solrDocs.add(sdoc);
			}
		}

		logger.info("updating and commiting docs");
		buildClient.add(solrDocs);
		buildClient.commit();
		logger.info("Data updated");
	}
}
