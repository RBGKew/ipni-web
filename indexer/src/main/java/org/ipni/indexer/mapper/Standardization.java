package org.ipni.indexer.mapper;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.solr.common.SolrInputDocument;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

public class Standardization {

	private final BufferedReader in;

	public Standardization(BufferedReader in) {
		this.in = in;
	}

	public SolrInputDocument build() {
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("stat_type_s", "Standardization");
		doc.addField("blob_s", buildJson(buildList()));

		return doc;
	}

	public List<List<String>> buildList() {
		return in.lines()
				.skip(1)
				.map(line -> line.split("\\|"))
				.map(row -> computeRow(row))
				.collect(transpose());
	}

	private List<String> computeRow(String[] row) {
		List<String> computed = new ArrayList<>();
		// take date part of 2006-03-01 00:00:00+00 style dates
		computed.add(row[0].split(" ")[0]);

		// compute percent of author citations standardised [standardised_author_citations / author_citations]
		computed.add(String.format("%.1f", 100 * (Double.parseDouble(row[6]) / Double.parseDouble(row[5]))));

		// compute percent of citations with standardised publication title [recs_with_publication_title_standardised / visible_records]
		computed.add(String.format("%.1f", 100 * (Double.parseDouble(row[8]) / Double.parseDouble(row[1]))));

		// compute percent of publications with plausible year [good_publication_year / visible_records]
		computed.add(String.format("%.1f", 100 * (Double.parseDouble(row[9]) / Double.parseDouble(row[1]))));

		// compute percent of records with no roman numerals in collation [(visible_records - recs_with_roman_numerals_in_collation) / visible_records]
		computed.add(String.format("%.1f", 100 * ((Double.parseDouble(row[1]) - Double.parseDouble(row[14])) / Double.parseDouble(row[1]))));

		return computed;
	}

	public String buildJson(List<List<String>> list) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writer().writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "{}";
		}
	}

	private Collector<List<String>, ?, List<List<String>>> transpose() {
		return Collector.of(
				// initialize map to store values in under column headings
				() -> new HashMap<String, List<String>>(),

				// Add each element of row to map under column title
				(result, row) -> {
					result.computeIfAbsent("date", k -> new ArrayList<>()).add(row.get(0));
					result.computeIfAbsent("standardised author citations", k -> new ArrayList<>()).add(row.get(1));
					result.computeIfAbsent("standardised publication titles", k -> new ArrayList<>()).add(row.get(2));
					result.computeIfAbsent("plausible publication year", k -> new ArrayList<>()).add(row.get(3));
					result.computeIfAbsent("no roman numerals in collation", k -> new ArrayList<>()).add(row.get(4));
				},

				// dummy combiner, not parallelisable
				(a, b) -> a,

				// convert each entry in map to row with column heading as first entry
				(computed) -> {
					return computed.entrySet().stream()
							.map(entry -> {
								return ImmutableList.<String>builder()
										.add(entry.getKey())
										.addAll(entry.getValue())
										.build();
							})
							.collect(Collectors.toList());
				}
				);
	}
}
