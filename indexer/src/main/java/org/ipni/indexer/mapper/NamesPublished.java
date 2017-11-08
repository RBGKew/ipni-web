package org.ipni.indexer.mapper;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import static java.util.stream.Collectors.*;

import org.apache.solr.common.SolrInputDocument;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NamesPublished {

	BufferedReader in;

	public NamesPublished(BufferedReader in) {
		this.in = in;
	}

	public Collection<SolrInputDocument> build() {
		return buildMap().entrySet().stream()
				.map(entry -> buildSolrDocument(entry))
				.collect(toList());
	}

	public SolrInputDocument buildSolrDocument(Entry<String, Map<String, Map<String, Integer>>> entry) {
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("stat_type_s", "namesPublished");
		doc.addField("year_i", entry.getKey());
		doc.addField("blob_s", buildJson(entry.getValue()));

		return doc;
	}

	public String buildJson(Map<String, Map<String, Integer>> map) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writer().writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "{}";
		}
	}

	public Map<String,Map<String, Map<String, Integer>>> buildMap() {
		return in.lines()
				.skip(1) // skip header line
				.map(line -> line.split("\\|"))
				.collect(groupingBy(record -> record[2], // group by year_published
						 groupingBy(record -> record[0], // then citation_type
						 groupingBy(record -> record[1], // then rank
						 // and then pull out the total by "summing" one number
						 summingInt(record -> Integer.parseInt(record[3]))))));
	}
}
