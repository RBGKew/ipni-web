package org.ipni.search;

import org.apache.solr.client.solrj.SolrQuery;

public abstract class QueryOption {
	
	public abstract void addQueryOption(String key, String value, SolrQuery query);

}
