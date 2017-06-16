package org.ipni.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;

public class SortQuery extends QueryOption {

	@Override
	public void addQueryOption(String key, String value, SolrQuery query) {
		switch(value){
		case "name_asc":
			query.setSort("family_s_lower", ORDER.asc);
			query.addSort("taxon_scientific_name_s_lower", ORDER.asc);
			break;
		case "name_desc":
			query.setSort("family_s_lower", ORDER.desc);
			query.addSort("taxon_scientific_name_s_lower", ORDER.desc);
			break;
		case "relevance":
			query.setSort("score", ORDER.desc);
			query.addSort("taxon.rank_s_lower", ORDER.asc);
			query.addSort("sortable", ORDER.asc);
			break;
		}
	}
}
