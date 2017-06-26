package org.ipni.response;

import java.util.List;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.SimpleOrderedMap;

import com.google.common.math.IntMath;

import java.math.RoundingMode;
import java.util.ArrayList;

import lombok.Data;

@Data
public class Response {

	private Integer totalResults;
	private Integer page;
	private Integer totalPages;
	private Integer perPage;
	private String sort;
	private List<Record> results = new ArrayList<Record>();

	public Response(QueryResponse queryResponse){
		SolrDocumentList queryResults = queryResponse.getResults();
		@SuppressWarnings("unchecked")
		SimpleOrderedMap<String> params = (SimpleOrderedMap<String>) queryResponse.getResponseHeader().get("params");
		totalResults = (int)queryResults.getNumFound();
		perPage = Integer.parseInt(params.get("rows"));
		page = IntMath.divide((int)queryResults.getStart(), perPage, RoundingMode.CEILING);
		totalPages = IntMath.divide(totalResults, perPage, RoundingMode.CEILING);

		queryResults.forEach(item->{
			Record result = new Record(item);
			results.add(result);
		});
	}


}
