package org.ipni.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.ipni.util.IdUtil;

public class IDQuery extends QueryOption {
	@Override
	public void addQueryOption(String key, String value, SolrQuery query) {
		switch(key) {
		case "name id": 
			query.setQuery(String.format("id:\"%s\"", IdUtil.fqName(value)));
			query.setFilterQueries(null);
			break;
		case "author id":
			query.setQuery(String.format("id:\"%s\"", IdUtil.fqAuthor(value)));
			query.setFilterQueries(null);
			break;
		case "publication id":
			query.setQuery(String.format("id:\"%s\"", IdUtil.fqPublication(value)));
			query.setFilterQueries(null);
			break;
		}
	}
}
