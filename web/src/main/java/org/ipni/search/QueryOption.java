package org.ipni.search;

import org.apache.solr.client.solrj.SolrQuery;

public abstract class QueryOption {
	
	protected String prepareValue(String field, String value) {
		// string fields with spaces should be quoted to exactly match multi-word values
		if(value.contains(" ") || value.contains("+")) {
			return String.format("\"%s\"", value);
		} else {
			return value;
		}
	}

	public abstract void addQueryOption(String key, String value, SolrQuery query);
}
