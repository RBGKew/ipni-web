package org.ipni.search;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.solr.client.solrj.SolrQuery;
import org.ipni.constants.FieldMapping;

public abstract class QueryOption {

	protected static final Pattern pattern = Pattern.compile("(^|\\s)(\\w{0,1}\\*|\\*\\w{0,1})(\\s|$)");

	protected String prepareValue(String field, String value) {
		/**
		 * If a wildcard match includes a floating * or a * with a single letter
		 * prefix/suffix /* remove that token. Solr will blow up otherwise. See:
		 * https://lucene.apache.org/solr/guide/6_6/other-parsers.html#OtherParsers-Limitations
		 */
		Matcher matcher = pattern.matcher(value);
		if (matcher.find()) {
			value = matcher.replaceAll("");
		}

		// string fields with spaces should be quoted to exactly match multi-word values
		if (value.contains(" ") || value.contains("+")) {
			value = String.format("\"%s\"", value);
		}

		return value;
	}

	/**
	 * When the value includes a wildcard match, enable the complex phrase query
	 * parser to produce more sensible results
	 */
	protected String prepareField(String field, String value) {
		if (FieldMapping.isValidField(field)) {
			field = FieldMapping.getSolrFieldFrom(field);
		}

		if (value.contains("*")) {
			return String.format("{!complexphrase inOrder=true}%s", field);
		}

		return field;
	}

	public abstract void addQueryOption(String key, String value, SolrQuery query);
}
