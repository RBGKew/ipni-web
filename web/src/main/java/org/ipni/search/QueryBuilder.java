package org.ipni.search;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrQuery;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import static org.ipni.constants.FieldMapping.*;

public class QueryBuilder {

	private static final ImmutableSet<String> nameQueryFields = new ImmutableSet.Builder<String>()
			.add(scientificName.solrField())
			.add(family.solrField())
			.add(genus.solrField())
			.add(species.solrField())
			.add(infraspecies.solrField())
			.add(hybridGenus.solrField())
			.build();

	private static final ImmutableSet<String> publicationQueryFields = ImmutableSet.of(
			title.solrField(),
			abbreviation.solrField());

	private static final ImmutableSet<String> authorQueryFields = ImmutableSet.of(
			authorForename.solrField(),
			authorSurname.solrField(),
			authorName.solrField(),
			authorAlternativeNames.solrField(),
			authorAlternativeAbbreviations.solrField(),
			authorStandardForm.solrField());

	private static final ImmutableSet<String> mainQueryFields = new ImmutableSet.Builder<String>()
			.add(scientificName.solrField())
			.add(family.solrField())
			.add(author.solrField())
			.add(publication.solrField())
			.addAll(publicationQueryFields)
			.addAll(authorQueryFields)
			.build();

	private static final QueryOption basicMapper = new SingleFieldFilterQuery();
	private static final DefaultQuery defaultQuery = new DefaultQuery();
	private static final RangeFilterQuery rangeQuery = new RangeFilterQuery();
	private static final RangeFilterQuery dateRangeQuery = new DateRangeFilterQuery();
	private static final IDQuery idQuery = new IDQuery();
	private static final QueryOption universalQuery = new CompositeQuery(new MultiFieldQuery(mainQueryFields), new AllTokensQuery(authorName));
	private static final QueryOption authorQuery = new CompositeQuery(new MultiFieldQuery(authorQueryFields), new AllTokensQuery(authorName));
	private static final QueryOption cursorQuery = new CursorQuery();

	private static final Map<String, QueryOption> queryMappings = new ImmutableMap.Builder<String, QueryOption>()
			.put("added after", dateRangeQuery)
			.put("added before", dateRangeQuery)
			.put("any", universalQuery)
			.put("author id", idQuery)
			.put("author team ids", idQuery)
			.put("author", authorQuery)
			.put("cursor", cursorQuery)
			.put("f", new FilterQuery())
			.put("modified after", dateRangeQuery)
			.put("modified before", dateRangeQuery)
			.put("name id", idQuery)
			.put("name", new MultiFieldQuery(nameQueryFields))
			.put("page", new PageNumberQuery())
			.put("perPage", new PageSizeQuery())
			.put("publication", new MultiFieldQuery(publicationQueryFields))
			.put("publication id", idQuery)
			.put("published in id", idQuery)
			.put("published after", rangeQuery)
			.put("published before", rangeQuery)
			.put("sort", new SortQuery())
			.build();

	private SolrQuery query;

	public QueryBuilder() {
		query = new SolrQuery().setRequestHandler("/select");
		addParam("sort", "name_asc");
		defaultQuery.add(query);
	}

	public QueryBuilder(Map<String, String> params) {
		this();

		for(Entry<String, String> param : params.entrySet() ){
			addParam(param.getKey(), param.getValue());
		}
	}

	public QueryBuilder addParam(String key, String value) {
		if(key.equals("callback") || key.equals("_")) {
			// do nothing, jsonp param
		} else if(key.equals("q")) {
			parseQuery(value);
		} else {
			mapParams(key, value);
		}

		return this;
	}

	private void mapParams(String key, String value) {
		if(queryMappings.containsKey(key)) {
			queryMappings.get(key).addQueryOption(key, value, query);
		} else {
			basicMapper.addQueryOption(key, value, query);
		}
	}

	private void parseQuery(String q) {
		for(String term : q.split("\\s*,\\s*")) {
			String[] terms = term.split("\\s*:\\s*");
			String key, value;
			if(terms.length == 2) {
				key = terms[0];
				value = terms[1];
			} else {
				key = "any";
				value = terms[0];
			}

			mapParams(key, value);
		}
	}

	public SolrQuery build() {
		return query;
	}
}
