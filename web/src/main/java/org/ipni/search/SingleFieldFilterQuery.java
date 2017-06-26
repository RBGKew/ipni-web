package org.ipni.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.ipni.constants.FieldMapping;

public class SingleFieldFilterQuery extends QueryOption {

	@Override
	public void addQueryOption(String key, String value, SolrQuery query) {
		value = prepareValue(key, value);
		
		if(FieldMapping.isValidField(key)) {
			key = FieldMapping.getSolrFieldFrom(key);

			if(query.getQuery() == null) {
				query.setQuery(String.format("%s:%s", key, value));
			} else {
				query.setQuery(String.format("%s AND %s:%s", query.getQuery(), key, value));
			}
		}
	}
}
