package org.ipni.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.ipni.response.Response;
import org.ipni.search.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/1/")
public class SearchController {

	private SolrClient solrClient = null;
	
	@Autowired
	public void setSolrClient() {
		this.solrClient = new HttpSolrClient("http://solr:8983/solr/ipni_view/");
	}
	
	@GetMapping("search/")
	public ResponseEntity<Response> search(@RequestParam Map<String,String> params) throws SolrServerException, IOException {
		QueryBuilder queryBuilder = new QueryBuilder();
		for(Entry<String, String> param : params.entrySet() ){
			queryBuilder.addParam(param.getKey(), param.getValue());
		}
		QueryResponse queryResponse = solrClient.query(queryBuilder.build());
		Response response = new Response(queryResponse);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
		
	}
}
