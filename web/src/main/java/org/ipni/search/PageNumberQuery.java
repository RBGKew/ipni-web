package org.ipni.search;

import org.apache.solr.client.solrj.SolrQuery;

public class PageNumberQuery extends QueryOption {

	@Override
	public void addQueryOption(String key, String value, SolrQuery query) {
		int rows = query.getRows() == null ? 50 : query.getRows();
		int start = Integer.valueOf(value) * rows;

		query.setStart(start);
	}
}
