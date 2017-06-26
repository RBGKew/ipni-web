package org.ipni.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.SolrResponseBase;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.util.NamedList;

public class AutoCompleteBuilder {

	private SolrQuery query = new SolrQuery().setRequestHandler("/suggest");

	private Integer pageSize = 5;

	private List<String> workingSuggesters;
	
	private boolean suggesterSet = false;

	private void addWorkingSuggester(String suggester) {
		if(workingSuggesters != null && workingSuggesters.contains(suggester)) {
			query.add("suggest.dictionary", suggester);
			suggesterSet = true;
		}
	}

	@SuppressWarnings("unchecked")
	public AutoCompleteBuilder setWorkingSuggesters(List<String> suggesters, SolrClient client) throws SolrServerException, IOException {
		workingSuggesters = new ArrayList<String>();
		ModifiableSolrParams params = new ModifiableSolrParams()
				.add("key", "suggest")
				.add("stats", "true");
		SolrQuery query = new SolrQuery().setRequestHandler("/admin/mbeans");
		query.add(params);
		SolrResponseBase response = client.query(query);
		NamedList<Object> responseStats = (NamedList<Object>) response.getResponse().findRecursive("solr-mbeans","OTHER","suggest","stats");
		for(Entry<String, Object> suggester : responseStats){
			if(!suggester.getKey().equals("totalSizeInBytes") 
					&& !(((String) suggester.getValue()).contains("sizeInBytes=0"))) {
				workingSuggesters.add(suggester.getKey());
			}
		}
		return this;
	}

	public AutoCompleteBuilder addSuggester(String suggester) {
			addWorkingSuggester(suggester);
		return this;
	}

	public AutoCompleteBuilder setSuggesters(List<String> suggesters) {
		for(String suggester : suggesters) {
			addSuggester(suggester);
		}
		return this;
	}

	public AutoCompleteBuilder setQuery(String string) {
		query.setQuery(string);
		return this;
	}

	public AutoCompleteBuilder pageSize(Integer pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public SolrQuery build () {
		if (suggesterSet) {
			query.add("suggest.count", pageSize.toString());
			return query;
		}
		return null;
	}
}

