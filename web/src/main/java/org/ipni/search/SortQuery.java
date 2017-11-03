package org.ipni.search;

import static org.ipni.constants.FieldMapping.*;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;

public class SortQuery extends QueryOption {

	@Override
	public void addQueryOption(String key, String value, SolrQuery query) {
		switch(value){
		case "name_asc":
			query.setSort("sortable", ORDER.asc);
			break;
		case "name_desc":
			query.setSort("sortable", ORDER.desc);
			break;
		case "published_asc":
			query.setSort(yearPublished.solrField(), ORDER.asc);
			break;
		case "published_desc":
			query.setSort(yearPublished.solrField(), ORDER.desc);
			break;
		}
	}
}
