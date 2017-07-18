package org.ipni.controller;

import org.ipni.model.Name;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SuggesterResponse;
import org.ipni.response.Response;
import org.ipni.search.AutoCompleteBuilder;
import org.ipni.search.QueryBuilder;
import org.ipni.service.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.ImmutableList;

@Controller
@RequestMapping("/api/1/")
public class SearchController {

	@Autowired
	private SolrClient solr;

	@Autowired
	private NameService names;

	private static List<String> suggesters = ImmutableList.<String>of("scientific-name");

	@GetMapping("search")
	public ResponseEntity<Response> search(@RequestParam Map<String,String> params) throws SolrServerException, IOException {
		QueryBuilder queryBuilder = new QueryBuilder();
		for(Entry<String, String> param : params.entrySet() ){
			queryBuilder.addParam(param.getKey(), param.getValue());
		}

		QueryResponse queryResponse = solr.query(queryBuilder.build());
		Response response = new Response(queryResponse);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("suggest")
	public ResponseEntity<SuggesterResponse> suggest(
			@RequestParam(value = "query", required = true) String queryString,
			@RequestParam(value = "page.size", required = false, defaultValue = "5") Integer pageSize
			) throws SolrServerException, IOException {

		SolrQuery query = new AutoCompleteBuilder()
				.setWorkingSuggesters(suggesters, solr)
				.setSuggesters(suggesters)
				.pageSize(pageSize)
				.setQuery(queryString)
				.build();

		if(query != null) {
			SuggesterResponse response = solr.query(query).getSuggesterResponse();
			return new ResponseEntity<SuggesterResponse>(response, HttpStatus.OK);
		}

		return null;
	}

	@GetMapping("urn:lsid:ipni.org:names:{id}")
	public ResponseEntity<Name> getName(@PathVariable String id) throws SolrServerException, IOException {
		return new ResponseEntity<Name>(names.load(id), HttpStatus.OK);
	}
}
