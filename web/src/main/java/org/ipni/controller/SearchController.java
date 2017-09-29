package org.ipni.controller;

import org.ipni.model.Author;
import org.ipni.model.Name;
import org.ipni.model.Publication;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.SuggesterResponse;
import org.ipni.response.Response;
import org.ipni.search.AutoCompleteBuilder;
import org.ipni.search.QueryBuilder;
import org.ipni.service.AuthorService;
import org.ipni.service.NameService;
import org.ipni.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	private AuthorService authors;

	@Autowired
	private NameService names;

	@Autowired
	private PublicationService publications;

	private static final List<String> suggesters = ImmutableList.<String>of("scientific-name", "publication", "author");

	@GetMapping("search")
	public ResponseEntity<Response> search(@RequestParam Map<String,String> params) throws SolrServerException, IOException {
		QueryBuilder query = new QueryBuilder(params);
		Response response = new Response(solr.query(query.build()));
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("suggest")
	public ResponseEntity<SuggesterResponse> suggest(
			@RequestParam(value = "query", required = true) String queryString,
			@RequestParam(value = "perPage", required = false, defaultValue = "5") Integer pageSize
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

	@GetMapping("urn:lsid:ipni.org:authors:{id}")
	public ResponseEntity<Author> getAuthor(@PathVariable String id) throws SolrServerException, IOException {
		return new ResponseEntity<Author>(authors.load(id), HttpStatus.OK);
	}

	@GetMapping("urn:lsid:ipni.org:publications:{id}")
	public ResponseEntity<Publication> getPublication(@PathVariable String id) throws SolrServerException, IOException {
		return new ResponseEntity<Publication>(publications.load(id), HttpStatus.OK);
	}
}
