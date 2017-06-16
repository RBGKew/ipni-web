package org.ipni.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.ipni.constants.FieldMapping;

public class SortQuery extends QueryOption {

	@Override
	public void addQueryOption(String key, String value, SolrQuery query) {
		switch(value){
		case "name_asc":
			query.setSort(FieldMapping.family.solrField(), ORDER.asc);
			query.addSort(FieldMapping.scientificName.solrField(), ORDER.asc);
			break;
		case "name_desc":
			query.setSort(FieldMapping.family.solrField(), ORDER.desc);
			query.addSort(FieldMapping.scientificName.solrField(), ORDER.desc);
			break;
		}
	}
}
