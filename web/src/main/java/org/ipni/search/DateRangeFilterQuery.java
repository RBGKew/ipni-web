package org.ipni.search;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.apache.solr.client.solrj.SolrQuery;

public class DateRangeFilterQuery extends RangeFilterQuery {
	@Override
	public void addQueryOption(String field, String value, SolrQuery query) {
		String formatted = LocalDate.parse(value, DateTimeFormatter.ISO_DATE)
				.atStartOfDay(ZoneOffset.UTC)
				.format(DateTimeFormatter.ISO_INSTANT);

		super.addQueryOption(field, formatted, query);
	}
}
