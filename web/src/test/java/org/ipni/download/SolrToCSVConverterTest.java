package org.ipni.download;

import static org.ipni.constants.FieldMapping.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringWriter;
import java.util.List;

import org.apache.solr.common.SolrDocument;
import org.ipni.constants.FieldMapping;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class SolrToCSVConverterTest {

	private final List<FieldMapping> fields = ImmutableList.<FieldMapping>of(scientificName, author, publication);

	private SolrDocument test;
	private StringWriter actual;

	@Before
	public void setupTestDocument() {
		test = new SolrDocument();
		test.putAll(ImmutableMap.<String, Object>of(
				scientificName.solrField(), "v1",
				author.solrField(), "v2",
				publication.solrField(), "v3"));
		actual = new StringWriter();
	}

	@Test
	public void addBasicSolrDocument() {
		try (SolrToCSVConverter converter = new SolrToCSVConverter(actual, fields)) {
			converter.addRow(test);
			String separator = converter.getFormat().getRecordSeparator();
			String expected = String.format("%s,%s,%s", scientificName.apiField(), author.apiField(), publication.apiField())
					+ separator + "v1,v2,v3" + separator;

			assertEquals(expected, actual.toString());

		} catch (Exception e) {
			System.err.println("Oh no! " + e.getMessage());
			assertTrue("Should not throw an exception", false);
		}
	}

	@Test
	public void addSolrDocumentWithCollectionValues() {
		try (SolrToCSVConverter converter = new SolrToCSVConverter(actual, fields)) {
			test.addField(scientificName.solrField(), "v1.1");

			converter.addRow(test);
			String separator = converter.getFormat().getRecordSeparator();
			String expected = String.format("%s,%s,%s", scientificName.apiField(), author.apiField(), publication.apiField())
					+ separator + "\"v1, v1.1\",v2,v3" + separator;

			assertEquals(expected, actual.toString());

		} catch (Exception e) {
			System.err.println("Oh no! " + e.getMessage());
			assertTrue("Should not throw an exception", false);
		}
	}

	@Test
	public void addSubsetOfValues() {
		List<FieldMapping> fields = ImmutableList.<FieldMapping>of(scientificName, publication);

		try (SolrToCSVConverter converter = new SolrToCSVConverter(actual, fields)) {
			converter.addRow(test);

			String separator = converter.getFormat().getRecordSeparator();
			String expected = String.format("%s,%s", scientificName.apiField(), publication.apiField()) + separator + "v1,v3" + separator;

			assertEquals(expected, actual.toString());

		} catch (Exception e) {
			System.err.println("Oh no! " + e.getMessage());
			assertTrue("Should not throw an exception", false);
		}
	}

	@Test
	public void handleMissingFields() {
		List<FieldMapping> fields = ImmutableList.<FieldMapping>of(scientificName, author, publication, inPowo);

		try (SolrToCSVConverter converter = new SolrToCSVConverter(actual, fields)) {
			converter.addRow(test);

			String separator = converter.getFormat().getRecordSeparator();
			String expected = String.format("%s,%s,%s,%s", scientificName.apiField(), author.apiField(), publication.apiField(), inPowo.apiField())
					+ separator + "v1,v2,v3," + separator;

			assertEquals(expected, actual.toString());

		} catch (Exception e) {
			System.err.println("Oh no! " + e.getMessage());
			assertTrue("Should not throw an exception", false);
		}
	}
}