package org.ipni.service;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.ipni.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

	@Autowired
	SolrClient solr;

	public Author load(String id) throws SolrServerException, IOException {
		ModifiableSolrParams params = new ModifiableSolrParams().add("fl", "*");
		if(!id.startsWith("urn:lsid:ipni.org:authors")) {
			id = "urn:lsid:ipni.org:authors:" + id;
		}

		SolrDocument result = solr.getById(id, params);

		return new Author(result);
	}

}