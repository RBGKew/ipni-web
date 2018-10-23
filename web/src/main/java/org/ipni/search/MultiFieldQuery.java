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

		if (value != null && !value.isEmpty()) {
			StringBuilder prefix = new StringBuilder();

			if (query.getQuery() != null) {
				prefix.append(query.getQuery());
				prefix.append(" AND ");
			}

			if (value.contains("*")) {
				prefix.append("{!complexphrase inOrder true}");
			}

			String newQuery = Joiner.on(" OR ").join(prepare(value));
			query.setQuery(String.format("%s(%s)", prefix, newQuery));
		}
	}

	private List<String> prepare(String value) {
		ArrayList<String> values = new ArrayList<>(fields.size());

		for (String field : fields) {
			values.add(String.format("%s:%s", field, prepareValue(field, value)));
		}

		return values;
	}
}