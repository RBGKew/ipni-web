package org.ipni.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.ipni.constants.FieldMapping;

import com.google.common.base.Joiner;

public class DefaultQuery extends QueryOption {

	@Override
	public void addQueryOption(String key, String value, SolrQuery query) {
		String[] filters = {
				String.format("%s:\"%s\"", FieldMapping.suppressed.solrField(), "false"),
				String.format("%s:\"%s\"", FieldMapping.topCopy.solrField(), "true"),
		};

		query.addFilterQuery(Joiner.on(" AND ").join(filters));
		query.setSort("sortable", ORDER.asc);
		query.addSort("id", ORDER.asc);
	}

	public void add(SolrQuery query) {
		addQueryOption(null, null, query);
	}
}
