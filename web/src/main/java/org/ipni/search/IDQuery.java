package org.ipni.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.ipni.constants.FieldMapping;
import org.ipni.util.IdUtil;

public class IDQuery extends QueryOption {
	@Override
	public void addQueryOption(String key, String value, SolrQuery query) {
		String[] blank = {};
		switch(key) {
		case "name id": 
			query.setQuery(String.format("id:\"%s\"", IdUtil.fqName(value)));
			query.setFilterQueries(blank);
			break;
		case "author id":
			query.setQuery(String.format("id:\"%s\"", IdUtil.fqAuthor(value)));
			query.setFilterQueries(blank);
			break;
		case "publication id":
			query.setQuery(String.format("id:\"%s\"", IdUtil.fqPublication(value)));
			query.setFilterQueries(blank);
			break;
		case "published in id":
			query.setQuery(String.format("%s:\"%s\"",
					FieldMapping.publicationId.solrField(), IdUtil.fqPublication(value)));
			break;
		case "author team ids":
			// Author teams are stored in a delimited format where each author in an author team is
			// in the form Govaerts@36710-1@1@aut
			query.setQuery(String.format("%s:*@%s@*aut*",
					FieldMapping.authorTeamIds.solrField(), IdUtil.idPart(value)));
			break;
		}
	}
}
