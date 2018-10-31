package org.ipni.search;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.solr.client.solrj.SolrQuery;
import org.ipni.constants.FieldMapping;

public class AllTokensQuery extends QueryOption {

	String field;

	public AllTokensQuery(FieldMapping field) {
		this.field = field.solrField();
	}

	@Override
	public void addQueryOption(String key, String value, SolrQuery query) {

		if(query.getQuery() == null) {
			query.setQuery(prepareValue(field, value));
		} else {
			query.setQuery(String.format("%s OR (%s)", query.getQuery(), prepareValue(field, value)));
		}
	}

	@Override
	protected String prepareValue(String field, String value) {
		String[] tokens = value.split("\\s+");
		if(tokens.length == 1) {
			return String.format("%s:\"%s\"", field, tokens[0]);
		} else {
			return Arrays.stream(tokens)
					.map(token -> String.format("%s:\"%s\"", field, token))
					.collect(Collectors.joining(" AND "));
		}
	}
}