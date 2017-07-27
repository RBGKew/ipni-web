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

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.util.ContentStreamBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tukaani.xz.XZInputStream;

@Controller
@RequestMapping("/test")
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
			.add("f.lookup_orthographic_variant_of_id.separator", ",");

	private SolrClient adminSolrClient = null;
	private SolrClient oldClient = null;
	private Logger logger = LoggerFactory.getLogger(Loader.class);
	private File indexFile = new File("/index.csv");
	private File coreFile = new File("/currentCore");
	private File updateFile = new File("/update.csv");

	private static String INDEX_FILE_URL = "http://storage.googleapis.com/kew-dev-backup/ipni_flat_all.csv.xz";
	private static String UPDATE_FILE_URL = "http://storage.googleapis.com/kew-dev-backup/powo_ipni_ids.txt.xz";

	@Autowired
	public void setadminSolrClient() {
		this.adminSolrClient = new HttpSolrClient.Builder("http://solr:8983/solr/").build();
	}

	@Autowired
	public void setoldClient() {
		this.oldClient = new HttpSolrClient.Builder("http://solr:8983/solr/ipni_2").build();
	}

	@GetMapping
	public void Load() {
		try {
			getCurrentCore();
			getFile(indexFile, INDEX_FILE_URL);
			loadData();
			getFile(updateFile, UPDATE_FILE_URL);
			updateData();
			switchCores();
			clearCore();
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getCurrentCore() {

	}

	public void getFile(File file, String filePathUrl) throws MalformedURLException, IOException {
		logger.info("Getting file from server..");
		if (file.exists()) {
			file.delete();
		}
		BufferedInputStream in = new BufferedInputStream(new URL(filePathUrl).openStream());
		XZInputStream unzip = new XZInputStream(in);
		FileOutputStream out = new FileOutputStream(file);
		final byte[] buffer = new byte[8192];
		int n = 0;
		while ((n = unzip.read(buffer)) != -1) {
			out.write(buffer, 0, n);
		}
		out.close();
		unzip.close();
	}

	public void loadData() throws MalformedURLException, IOException, SolrServerException {
		logger.info("Loading Data to Solr...");
		ContentStreamBase.FileStream stream = new ContentStreamBase.FileStream(indexFile);
		stream.setContentType("application/csv");
		ContentStreamUpdateRequest request = new ContentStreamUpdateRequest("/ipni_2/update");
		request.setParams(params);
		request.addContentStream(stream);
		adminSolrClient.request(request);
		logger.info("Data loaded");
	}

	public void switchCores() throws SolrServerException, IOException {
		logger.info("Swapping cores..");
		CoreAdminRequest.swapCore("ipni_1", "ipni_2", adminSolrClient);
		logger.info("Cores swapped");
	}

	public void clearCore() throws SolrServerException, IOException {
		logger.info("Clearing old core");
		oldClient.deleteByQuery("*:*");
		oldClient.commit();
		logger.info("Old core cleared");
	}

	public void updateData() throws MalformedURLException, IOException, SolrServerException {
		logger.info("Updating Solr Data...");

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(updateFile), "UTF8"));
		String IPNI_URI = "urn:lsid:ipni.org:names:";
		String sCurrentLine;
		String recordIdClean = null;

		Collection<SolrInputDocument> solrDocs = new ArrayList<SolrInputDocument>();

		while ((sCurrentLine = br.readLine()) != null) {
			recordIdClean = sCurrentLine.replaceFirst(IPNI_URI, "");
			SolrInputDocument sdoc = new SolrInputDocument();
			sdoc.addField("id", recordIdClean);
			sdoc.addField("powo_b", Collections.singletonMap("set", true));
			solrDocs.add(sdoc);
		}

		br.close();
		logger.info("updating and commiting docs");
		oldClient.add(solrDocs);
		oldClient.commit();
		logger.info("Data updated");
	}
}
