package org.ipni.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.ipni.constants.FieldMapping;

import com.google.common.base.Joiner;

public class DefaultQuery extends QueryOption {

	@Override
	public void addQueryOption(String key, String value, SolrQuery query) {
		String[] filters = {
				String.format("%s:\"%s\"", FieldMapping.ipniRecordType.solrField(), "a"),
				String.format("%s:\"%s\"", FieldMapping.ipniRecordType.solrField(), "p"),
				String.format("%s:\"%s\"", FieldMapping.suppressed.solrField(), "false"),
		};

		query.addFilterQuery(Joiner.on(" OR ").join(filters));
	}

	public void add(SolrQuery query) {
		addQueryOption(null, null, query);
	}
}
