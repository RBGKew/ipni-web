package org.ipni.indexer.mapper;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Stream;

import org.apache.solr.common.SolrInputDocument;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Streams;

public class RecordActivity {

	BufferedReader in;

	public RecordActivity(BufferedReader in) {
		this.in = in;
	}
	public Collection<SolrInputDocument> build() {
		Map<String,Map<String, Map<String, List<Integer>>>> map = buildMap();

		return Stream.concat(
				map.get("Addition").entrySet().stream().map(entry -> buildSolrDocument(entry, "Addition")),
				map.get("Update").entrySet().stream().map(entry -> buildSolrDocument(entry, "Update")))
				.collect(toList());
	}

	public SolrInputDocument buildSolrDocument(Entry<String, Map<String, List<Integer>>> entry, String type) {
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("stat_type_s", type);
		doc.addField("year_i", entry.getKey());
		doc.addField("blob_s", buildJson(entry.getValue()));

		return doc;
	}

	public String buildJson(Map<String, List<Integer>> map) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writer().writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "{}";
		}
	}

	public Map<String,Map<String, Map<String, List<Integer>>>> buildMap() {
		return in.lines()
				.skip(1) // skip header line
				.map(line -> line.split("\\|"))
				.collect(groupingBy(record -> record[1], // group by activity type (Addition or Update)
						groupingBy(record -> record[3],  // then year
						groupingBy(record -> record[0],  // then object type (Authors, Name citations, or Publications)
						collectMonthlyTotals()))));       // then pull out totals into array
	}

	private Collector<String[], ?, List<Integer>> collectMonthlyTotals() {
		return Collector.of(
				// initialize an array full of zeros
				() -> Arrays.stream(new Integer[12]).map(e -> 0).toArray(Integer[]::new),
				// put the total in the position indexed by month
				(result, record) -> result[Integer.parseInt(record[2]) - 1] = Integer.parseInt(record[4]),
				// combine partial computations by summing corresponding elements
				(pr1, pr2) -> Streams.zip(Arrays.stream(pr1), Arrays.stream(pr2), Integer::sum).toArray(Integer[]::new),
				Arrays::asList);
	}
}
