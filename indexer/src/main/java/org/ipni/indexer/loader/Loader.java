package org.ipni.indexer.loader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.util.ContentStream;
import org.apache.solr.common.util.ContentStreamBase;
import org.apache.solr.common.util.ContentStreamBase.ByteArrayStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tukaani.xz.XZInputStream;

import com.google.common.io.ByteStreams;

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
	private File file = new File("/index.csv");
	private File coreFile = new File("/currentCore");
	
	@Autowired
	public void setadminSolrClient() {
		this.adminSolrClient = new HttpSolrClient.Builder("http://solr:8983/solr/").build();
	}
	
	@Autowired
	public void setoldClient() {
		this.oldClient = new HttpSolrClient.Builder("http://solr:8983/solr/ipni_2").build();
	}
	
	@GetMapping
	public void Load(){
		try {
			getCurrentCore();
			getFile();
			loadData();
			switchCores();
			clearCore();
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void getCurrentCore(){
		
	}
	
	public void getFile() throws MalformedURLException, IOException {
		logger.info("Getting file from server..");
		if(file.exists()){
			file.delete();
		}
		BufferedInputStream in = new BufferedInputStream(new URL("http://storage.googleapis.com/kew-dev-backup/ipni_flat_all.csv.xz").openStream());
		XZInputStream unzip = new XZInputStream(in);
		FileOutputStream out = new FileOutputStream(file);
		final byte[] buffer = new byte[8192];
		int n = 0;
		while ((n = unzip.read(buffer)) != -1){
			out.write(buffer, 0, n);
		}
		out.close();
		unzip.close();
	}
	
	
	public void loadData() throws MalformedURLException, IOException, SolrServerException{
		logger.info("Loading Data to Solr...");
		ContentStreamBase.FileStream stream = new ContentStreamBase.FileStream(file);
		stream.setContentType("application/csv");
		ContentStreamUpdateRequest request = new ContentStreamUpdateRequest("/ipni_2/update");
		request.setParams(params);
		request.addContentStream(stream);
		adminSolrClient.request(request);
		logger.info("Data loaded");
	}
	
	
	public void switchCores() throws SolrServerException, IOException{
		logger.info("Swapping cores..");
		CoreAdminRequest.swapCore("ipni_1", "ipni_2", adminSolrClient);
		logger.info("Cores swapped");
	}
	
	public void clearCore() throws SolrServerException, IOException{
		logger.info("Clearing old core");
		oldClient.deleteByQuery("*:*");
		oldClient.commit();
		logger.info("Old core cleared");
	}
	
	
}
