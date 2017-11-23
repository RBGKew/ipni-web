package org.ipni.service;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

	@Autowired
	SolrClient solr;

	public String getNamesPublishedInYear(Integer year) throws SolrServerException, IOException {
		return buildResponse(query("namesPublished", year));
	}

	public String getRecordsAdded(Integer year) throws SolrServerException, IOException {
		return buildResponse(query("Addition", year));
	}

	public String getRecordsUpdated(Integer year) throws SolrServerException, IOException {
		return buildResponse(query("Update", year));
	}

	public String getStandardization() throws SolrServerException, IOException {
		SolrQuery query = new SolrQuery();
		query.addFilterQuery("stat_type_s:Standardization");
		SolrDocumentList results = solr.query(query).getResults();

		if(!results.isEmpty()) {
			return (String)results.get(0).getFieldValue("blob_s");
		} else {
			return "{\"error\":\"no standardization stats found\"}";
		}
	}

	private SolrDocumentList query(String type, Integer year) throws SolrServerException, IOException {
		SolrQuery query = new SolrQuery();
		query.addFilterQuery("stat_type_s:" + type);
		query.setQuery("year_i:" + year);
		return solr.query(query).getResults();
	}

	private String buildResponse(SolrDocumentList results) {
		if(!results.isEmpty()) {
			return (String)results.get(0).getFieldValue("blob_s");
		} else {
			return "{\"error\":\"no stats found for given year\"}";
		}
	}
}
