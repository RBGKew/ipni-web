package org.ipni.search;

import org.apache.solr.client.solrj.SolrQuery;

public class RangeFilterQuery extends QueryOption {
	@Override
	public void addQueryOption(String field, String value, SolrQuery query) {
		Range range = new Range(field, value);
		if(range.valid()) {
			query.addFilterQuery(range.build());
		}
	}
}
