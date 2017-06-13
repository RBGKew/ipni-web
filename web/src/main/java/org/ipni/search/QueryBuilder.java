package org.ipni.search;

import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

public class QueryBuilder {
	
	private static Logger logger = LoggerFactory.getLogger(QueryBuilder.class);
	private static final Map<String, QueryOption> queryMappings = new ImmutableMap.Builder<String, QueryOption>()
			.build();
	private SolrQuery query = new SolrQuery().setRequestHandler("/select");
	private static final QueryOption basicMapper = new  SingleFieldFilterQuery();
	
	public QueryBuilder addParam(String key, String value) {
		if(key.equals("q")) {
			parseQuery(value);
		} else {
			mapParams(key, value);
		}

		return this;
	}
	
	private void mapParams(String key, String value) {

		if(queryMappings.containsKey(key)) {
			queryMappings.get(key).addQueryOption(key, value, query);
		} else {
			basicMapper.addQueryOption(key, value, query);
		}
	}
	
	private void parseQuery(String q) {
		for(String term : q.split("\\s*,\\s*")) {
			String[] terms = term.split("\\s*:\\s*");
			String key, value;
			if(terms.length == 2) {
				key = terms[0];
				value = terms[1];
			} else {
				key = "any";
				value = terms[0];
			}

			mapParams(key, value);
		}
	}
	
	public SolrQuery build(){
		return query;
	}
}
