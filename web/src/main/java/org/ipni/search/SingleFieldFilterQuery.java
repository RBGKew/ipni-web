package org.ipni.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.ipni.constants.FieldMapping;

public class SingleFieldFilterQuery extends QueryOption {

	@Override
	public void addQueryOption(String field, String value, SolrQuery query) {
		if (FieldMapping.isValidField(field)) {
			value = prepareValue(field, value);
			field = prepareField(field, value);

			if (query.getQuery() == null) {
				query.setQuery(String.format("%s:%s", field, value));
			} else {
				query.setQuery(String.format("%s AND %s:%s", query.getQuery(), field, value));
			}
		}
	}
}