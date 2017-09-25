package org.ipni.download;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.ipni.constants.FieldMapping;

import com.google.common.io.Files;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Packager {

	private SolrDocumentList results;

	private Map<String, List<FieldMapping>> exportFields;

	private File workspace;

	public Packager(SolrDocumentList results, Map<String, List<FieldMapping>> exportFields) {
		this.results = results;
		this.exportFields = exportFields;
		this.workspace = Files.createTempDir();
	}

	public File create() throws IOException {
		String name = String.format("ipni-download-%s.zip", LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE));

		List<File> files = results.stream()
				.collect(Collectors.groupingBy(result -> classify(result)))
				.entrySet().stream()
				.map(entry -> toCsv(entry))
				.collect(Collectors.toList());

		log.debug("Zipping files: {}", files.toString());

		return ZipWriter.createZipFile(new File(workspace, name), files);
	}

	public void cleanup() throws IOException {
		java.nio.file.Files.walk(workspace.toPath())
			.map(Path::toFile)
			.sorted((o1, o2) -> o2.compareTo(o1))
			.forEach(File::delete);
	}

	private File toCsv(Entry<String, List<SolrDocument>> entry) {
		File dest = new File(workspace, entry.getKey() + ".csv");
		String type = entry.getKey();

		try (FileWriter file = new FileWriter(dest);
				SolrToCSVConverter converter = new SolrToCSVConverter(file, exportFields.get(type))) {
			for(SolrDocument row : entry.getValue()) {
				converter.addRow(row);
			}
		} catch (Exception e) {
			log.error("Error in Packager.writeToCsv: {}", e.getMessage());
		}

		return dest;
	}

	private String classify(SolrDocument result) {
		switch((String) result.getOrDefault(FieldMapping.ipniRecordType.solrField(), "")) {
		case "a": return "authors";
		case "p": return "publications";
		default: return "citations";
		}
	}
}
