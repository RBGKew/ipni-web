package org.ipni.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NameAuthorTest {

	@Test
	public void testNameAuthorParsing() {
		NameAuthor na = new NameAuthor("X.Y.Wang@37842-1@4@autEx");

		assertEquals("X.Y.Wang", na.getName());
		assertEquals("37842-1", na.getId());
		assertEquals(4, na.getOrder());
		assertEquals("autEx", na.getType());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidString() {
		new NameAuthor("A@2@aa");
	}

	@Test
	public void testNullId() {
		NameAuthor na = new NameAuthor("X.Y.Wang@0-null@4@autEx");
		NameAuthor na2 = new NameAuthor("X.Y.Wang@0-0@4@autEx");

		assertEquals(null, na.getId());
		assertEquals(null, na2.getId());
	}
}
