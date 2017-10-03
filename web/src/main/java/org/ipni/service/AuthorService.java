package org.ipni.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.ipni.constants.FieldMapping;
import org.ipni.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

	@Autowired
	SolrClient solr;

	public Author load(String id) throws SolrServerException, IOException {
		return load(id, FieldMapping.all);
	}

	public Author load(String id, FieldMapping... fields) throws SolrServerException, IOException {
		if(id == null)  {
			return null;
		}

		String fieldList = Arrays.asList(fields).stream()
				.map(FieldMapping::solrField)
				.collect(Collectors.joining(","));

		ModifiableSolrParams params = new ModifiableSolrParams().add("fl", fieldList);
		if(!id.startsWith("urn:lsid:ipni.org:authors")) {
			id = "urn:lsid:ipni.org:authors:" + id;
		}

		SolrDocument result = solr.getById(id, params);

		return new Author(result);
	}

}
