package org.ipni.search;

import static org.ipni.constants.FieldMapping.*;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;

public class SortQuery extends QueryOption {

	@Override
	public void addQueryOption(String key, String value, SolrQuery query) {
		switch(value){
		case "name_asc":
			query.setSort(family.solrField(), ORDER.asc)
					.addSort(infrafamily.solrField(), ORDER.asc)
					.addSort(genus.solrField(), ORDER.asc)
					.addSort(infragenus.solrField(), ORDER.asc)
					.addSort(species.solrField(), ORDER.asc)
					.addSort(infraspecies.solrField(), ORDER.asc);
			break;
		case "name_desc":
			query.setSort(family.solrField(), ORDER.desc)
					.addSort(infrafamily.solrField(), ORDER.desc)
					.addSort(genus.solrField(), ORDER.desc)
					.addSort(infragenus.solrField(), ORDER.desc)
					.addSort(species.solrField(), ORDER.desc)
					.addSort(infraspecies.solrField(), ORDER.desc);
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
