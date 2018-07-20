package org.ipni.search;

import java.util.Arrays;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;

public class CompositeQuery extends QueryOption {

	List<QueryOption> options;

	public CompositeQuery(QueryOption ...options) {
		this.options = Arrays.asList(options);
	}

	@Override
	public void addQueryOption(String key, String value, SolrQuery query) {
		for(QueryOption option : options) {
			option.addQueryOption(key, value, query);
		}
	}

}
