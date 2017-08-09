package org.ipni.response;

import java.util.List;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.ipni.constants.FieldMapping;
import org.ipni.model.Author;
import org.ipni.model.Name;
import org.ipni.model.Publication;

import com.google.common.base.Strings;
import com.google.common.math.LongMath;

import java.math.RoundingMode;
import java.util.ArrayList;

import lombok.Data;

@Data
public class Response {

	private Long totalResults;
	private Long page;
	private Long totalPages;
	private Long perPage;
	private String sort;
	private List<Object> results = new ArrayList<>();

	public Response(QueryResponse queryResponse) {
		SolrDocumentList queryResults = queryResponse.getResults();
		@SuppressWarnings("unchecked")
		SimpleOrderedMap<String> params = (SimpleOrderedMap<String>) queryResponse.getResponseHeader().get("params");
		totalResults = queryResults.getNumFound();
		perPage = Long.parseLong(params.get("rows"));
		page = LongMath.divide(queryResults.getStart(), perPage, RoundingMode.CEILING);
		totalPages = LongMath.divide(totalResults, perPage, RoundingMode.CEILING);

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
				results.add(new Name(item));
			}
		}
	}
}