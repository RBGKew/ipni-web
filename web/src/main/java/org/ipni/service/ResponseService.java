package org.ipni.service;

import java.io.IOException;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.ipni.constants.FieldMapping;
import org.ipni.model.Author;
import org.ipni.model.Name;
import org.ipni.model.Publication;
import org.ipni.response.Response;
import org.ipni.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.math.LongMath;

@Service
public class ResponseService {

	@Autowired
	SolrClient solr;

	@Autowired
	PublicationService publicationService;

	@SuppressWarnings("unchecked")
	public Response load(SolrQuery query) throws SolrServerException, IOException {
		QueryResponse queryResponse = solr.query(query);
		SolrDocumentList queryResults = queryResponse.getResults();
		SimpleOrderedMap<String> params = (SimpleOrderedMap<String>) queryResponse.getResponseHeader().get("params");

		long totalResults = queryResults.getNumFound();
		long perPage = Long.parseLong(params.get("rows"));
		Response.ResponseBuilder response = Response.builder()
				.totalResults(totalResults)
				.perPage(perPage)
				.totalPages(LongMath.divide(totalResults, perPage, RoundingMode.CEILING))
				.page(LongMath.divide(queryResults.getStart(), perPage, RoundingMode.CEILING))
				.results(mapResults(queryResults, loadRelatedPublications(queryResults)))
				.cursor(queryResponse.getNextCursorMark());

		return response.build();
	}

	private List<Object> mapResults(SolrDocumentList queryResults, Map<String, Publication> publications) {
		List<Object> results = new ArrayList<>();
		for(SolrDocument item : queryResults) {
			String type = Strings.nullToEmpty((String)item.getFieldValue(FieldMapping.ipniRecordType.solrField()));
			switch(type) {
			case "a":
				results.add(new Author(item));
				break;
			case "p":
				results.add(new Publication(item));
				break;
			default:
				Name name = new Name(item);
				name.setLinkedPublication(publications.get(name.getPublicationId()));
				results.add(name);
			}
		}

		return results;
	}

	private Map<String, Publication> loadRelatedPublications(SolrDocumentList queryResults) throws SolrServerException, IOException {
		List<String> publicationIds = queryResults.stream()
				.map(res -> res.getFirstValue(FieldMapping.publicationId.solrField()))
				.filter(Objects::nonNull)
				.map(id -> IdUtil.fqPublication(id.toString()))
				.unordered()
				.distinct()
				.collect(Collectors.toList());

		return publicationService.load(publicationIds).stream()
				.collect(Collectors.toMap(Publication::getId, Function.identity()));
	}
}
