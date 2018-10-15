package org.ipni.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.ipni.constants.FieldMapping;
import org.ipni.exceptions.NotFoundException;
import org.ipni.model.Publication;
import org.ipni.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

@Service
public class PublicationService {

	@Autowired
	SolrClient solr;

	public List<Publication> load(Collection<String> ids, FieldMapping... fields) throws SolrServerException, IOException {
		if(ids == null)  {
			return new ArrayList<>();
		}

		String fieldList = Arrays.asList(fields).stream()
				.map(FieldMapping::solrField)
				.collect(Collectors.joining(","));
		ModifiableSolrParams params = new ModifiableSolrParams().add("fl", fieldList);

		ids = ids.stream()
				.map(id -> IdUtil.fqPublication(id))
				.collect(Collectors.toList());

		if(!ids.isEmpty()) {
			SolrDocumentList result = solr.getById(ids, params);

			if (result == null) {
				throw new NotFoundException();
			}

			if(result != null) {
				return result.stream()
						.map(Publication::new)
						.collect(Collectors.toList());
			}
		}

		return new ArrayList<>();
	}

	public Publication load(String id, FieldMapping...fields) throws SolrServerException, IOException {
		if(id == null) return null;

		List<Publication> res = load(ImmutableList.<String>of(id), fields);
		if(res != null && res.size() == 1) {
			return res.get(0);
		} else {
			return null;
		}
	}

	public List<Publication> load(Collection<String> ids) throws SolrServerException, IOException {
		return load(ids, FieldMapping.all);
	}

	public Publication load(String id) throws SolrServerException, IOException {
		return load(id, FieldMapping.all);
	}
}
