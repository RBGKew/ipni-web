package org.ipni.service;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.ipni.model.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NameService {

	@Autowired
	SolrClient solr;
	
	public Name load(String id) throws SolrServerException, IOException {
		SolrDocument result = solr.getById(id);
		return new Name(result);
	}

}
