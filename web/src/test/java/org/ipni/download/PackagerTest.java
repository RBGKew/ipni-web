package org.ipni.download;

import static org.junit.Assert.assertTrue;
import static org.ipni.constants.FieldMapping.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.ipni.constants.FieldMapping;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;

public class PackagerTest {

	File result;
	File workspace;
	Packager packager;

	@Before
	public void setup() throws IOException {

		SolrDocumentList results = new SolrDocumentList();
		SolrDocument citations = new SolrDocument();
		SolrDocument authors = new SolrDocument();
		SolrDocument publications = new SolrDocument();

		citations.put("c1", "v1");
		citations.put("c2", "v2");
		citations.put("c3", "v3");

		authors.put(ipniRecordType.solrField(), "a");
		authors.put("a1", "v1");
		authors.put("a2", "v2");
		authors.put("a3", "v3");

		publications.put(ipniRecordType.solrField(), "p");
		publications.put("p1", "v1");
		publications.put("p2", "v2");
		publications.put("p3", "v3");

		results.add(citations);
		results.add(authors);
		results.add(publications);

		Map<String, List<FieldMapping>> fields = ImmutableMap.<String, List<FieldMapping>>of(
				"authors", ImmutableList.<FieldMapping>of(authorName, authorSurname, authorStandardForm),
				"citations", ImmutableList.<FieldMapping>of(scientificName, author, publication),
				"publications", ImmutableList.<FieldMapping>of(title, date, abbreviation));

		packager = new Packager(results, fields);

		result = packager.create();

		workspace = result.getParentFile();
	}

	@After
	public void cleanup() throws IOException {
		packager.cleanup();
	}

	@Test
	public void createsAuthorsFile() {
		File file = new File(workspace, "authors.txt");
		String expectedHeader = String.format("%s,%s,%s", authorName.apiField(), authorSurname.apiField(), authorStandardForm.apiField());

		assertTrue("authors.csv file should exist in workspace", file.exists());
		assertTrue("authors.csv should only have author field headers", hasHeader(file, expectedHeader));
	}

	@Test
	public void createsCitationsFile() {
		File file = new File(workspace, "citations.txt");
		String expectedHeader = String.format("%s,%s,%s", scientificName.apiField(), author.apiField(),publication.apiField());

		assertTrue("citations.csv file should exist in workspace", file.exists());
		assertTrue("citations.csv should only have citation field headers", hasHeader(file, expectedHeader));
	}

	@Test
	public void createsPublicationsFile() {
		File file = new File(workspace, "publications.txt");
		String expectedHeader = String.format("%s,%s,%s", title.apiField(), date.apiField(), abbreviation.apiField());

		assertTrue("publications.csv file should exist in workspace", file.exists());
		assertTrue("publications.csv should only have publication field headers", hasHeader(file, expectedHeader));
	}

	@Test
	public void createsZipFile() {
		assertTrue("final .zip should exist in workspace", result.exists());
	}

	private boolean hasHeader(File file, String header) {
		try {
			String firstLine = Files.asCharSource(file, Charset.defaultCharset()).readFirstLine();
			return header.equals(firstLine);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
