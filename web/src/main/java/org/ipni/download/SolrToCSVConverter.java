package org.ipni.download;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.solr.common.SolrDocument;
import org.ipni.constants.FieldMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

import lombok.Getter;

public class SolrToCSVConverter implements AutoCloseable {

	private final Logger log = LoggerFactory.getLogger(SolrToCSVConverter.class);

	private final CSVPrinter csv;

	@Getter
	private final List<FieldMapping> exportFields;

	@Getter
	private final CSVFormat format;

	public SolrToCSVConverter(Appendable output, List<FieldMapping> exportFields) {
		this.exportFields = exportFields;
		String[] header = exportFields.stream()
				.map(FieldMapping::apiField)
				.collect(Collectors.toList())
				.toArray(new String[]{});

		this.format = CSVFormat.DEFAULT
				.withHeader(header)
				.withTrim();
		try {
			csv = new CSVPrinter(output, format);
		} catch (IOException e) {
			log.error("Error instantiating CSVPrinter: {}", e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public void addRow(SolrDocument row) throws IOException {
		List<String> empty = new ArrayList<>();
		List<String> csvRow = exportFields.stream()
				.map(field -> row.getFieldValues(field.solrField()))
				.map(values -> values == null ? empty : values)
				.map(values -> Joiner.on(", ").join(values))
				.collect(Collectors.toList());

		csv.printRecord(csvRow);
	}

	@Override
	public void close() throws Exception {
		try {
			csv.close();
		} catch (IOException e) {
			log.error("Could not close CSVPrinter: {}", e.getMessage());
		}
	}
}