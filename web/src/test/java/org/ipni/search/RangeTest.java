package org.ipni.search;

import static org.junit.Assert.assertEquals;

import org.ipni.constants.FieldMapping;
import org.junit.Test;

public class RangeTest {

	@Test
	public void testPublishedBefore() {
		String expected = FieldMapping.yearPublished.solrField() + ":[* TO 1900]";
		String actual = new Range("published before", "1900").build();
		assertEquals(expected, actual);
	}

	@Test
	public void testPublishedAfter() {
		String expected = FieldMapping.yearPublished.solrField() + ":[1900 TO *]";
		String actual = new Range("published after", "1900").build();
		assertEquals(expected, actual);
	}

	@Test
	public void testCreatedBefore() {
		String expected = FieldMapping.created.solrField() + ":[* TO 1900]";
		String actual = new Range("added before", "1900").build();
		assertEquals(expected, actual);
	}

	@Test
	public void testCreatedAfter() {
		String expected = FieldMapping.created.solrField() + ":[1900 TO *]";
		String actual = new Range("added after", "1900").build();
		assertEquals(expected, actual);
	}

	@Test
	public void testUpdatedBefore() {
		String expected = FieldMapping.updated.solrField() + ":[* TO 1900]";
		String actual = new Range("modified before", "1900").build();
		assertEquals(expected, actual);
	}

	@Test
	public void testUpdatedAfter() {
		String expected = FieldMapping.updated.solrField() + ":[1900 TO *]";
		String actual = new Range("modified after", "1900").build();
		assertEquals(expected, actual);
	}
}
