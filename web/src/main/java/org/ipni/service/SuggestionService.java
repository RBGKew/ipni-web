package org.ipni.service;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.SuggesterResponse;
import org.ipni.search.AutoCompleteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

@Service
public class SuggestionService {

	@Autowired
	SolrClient solr;

	private static final List<String> suggesters = ImmutableList.<String>of("scientific-name", "publication", "author");

	public SuggesterResponse load(String queryString) throws SolrServerException, IOException {

		SolrQuery query = new AutoCompleteBuilder()
				.setWorkingSuggesters(suggesters, solr)
				.setSuggesters(suggesters)
				.pageSize(5)
				.setQuery(queryString)
				.build();

		if(query != null) {
			return solr.query(query).getSuggesterResponse();
		}

		return null;
	}
}
