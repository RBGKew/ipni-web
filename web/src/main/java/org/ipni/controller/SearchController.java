package org.ipni.controller;

import org.ipni.model.Author;
import org.ipni.model.Name;
import org.ipni.model.Publication;

import java.io.IOException;
import java.util.Map;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.SuggesterResponse;
import org.ipni.response.Response;
import org.ipni.search.QueryBuilder;
import org.ipni.service.AuthorService;
import org.ipni.service.NameService;
import org.ipni.service.PublicationService;
import org.ipni.service.ResponseService;
import org.ipni.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/1/")
public class SearchController {

	@Autowired
	private AuthorService authors;

	@Autowired
	private NameService names;

	@Autowired
	private PublicationService publications;

	@Autowired
	private SuggestionService suggestions;

	@Autowired
	private ResponseService responses;

	@GetMapping("search")
	public ResponseEntity<Response> search(@RequestParam Map<String,String> params) throws SolrServerException, IOException {
		SolrQuery query = new QueryBuilder(params).build();
		Response response = responses.load(query);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("suggest")
	public ResponseEntity<SuggesterResponse> suggest(@RequestParam(value = "query", required = true) String query) throws SolrServerException, IOException {
		return new ResponseEntity<SuggesterResponse>(suggestions.load(query), HttpStatus.OK);
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
