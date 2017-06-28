package org.ipni.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.ipni.constants.FieldMapping;

import com.google.common.base.Joiner;

public class FilterQuery extends QueryOption {

	@Override
	public void addQueryOption(String key, String value, SolrQuery query) {
		String[] filters = value.split(",");
		List<String> selected = new ArrayList<String>();
		for(String filter : filters) {
			switch(filter) {
			case "f_familial":
				selected.add("fam.");
				break;
			case "f_infrafamilial":
				selected.add("infrafam.");
				break;
			case "f_generic":
				selected.add("gen.");
				break;
			case "f_infrageneric":
				selected.add("infragen.");
				break;
			case "f_specific":
				selected.add("spec.");
				break;
			case "f_infraspecific":
				selected.add("infraspec.");
				break;
			case "f_hybrid":
				break;
			}
		}

		String filter = String.format("%s: (%s)", 
				FieldMapping.rank.solrField(),
				Joiner.on(" ").join(selected));

		query.addFilterQuery(filter);
	}
}
