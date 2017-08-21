package org.ipni.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import com.google.common.base.Joiner;

public class MultiFieldQuery extends QueryOption {

	Set<String> fields;

	public MultiFieldQuery(Set<String> fields) {
		this.fields = fields;
	}

	@Override
	public void addQueryOption(String key, String value, SolrQuery query) {
		if(value != null && !value.isEmpty()) {
			String newQuery = Joiner.on(" OR ").join(prepareValues(value));
			if(query.getQuery() == null) {
				query.setQuery("(" + newQuery + ")");
			} else {
				query.setQuery(query.getQuery() + " AND (" + newQuery + ")");
			}
		}
	}

	private List<String> prepareValues(String value) {
		ArrayList<String> values = new ArrayList<>(fields.size());

		for(String field : fields) {
			values.add(String.format("%s:%s", field, prepareValue(field, value)));
		}

		return values;
	}
}