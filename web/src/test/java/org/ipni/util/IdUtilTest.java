package org.ipni.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IdUtilTest {

	@Test
	public void testFqAuthorOnNonFqId() {
		String input = "1234-2";
		String expected = "urn:lsid:ipni.org:authors:1234-2";
		String actual = IdUtil.fqAuthor(input);

		assertEquals(expected, actual);
	}

	@Test
	public void testFqAuthorOnFqId() {
		String input = "urn:lsid:ipni.org:authors:1234-2";
		String expected = "urn:lsid:ipni.org:authors:1234-2";
		String actual = IdUtil.fqAuthor(input);

		assertEquals(expected, actual);
	}

	@Test
	public void testFqNameOnNonFqId() {
		String input = "1234-2";
		String expected = "urn:lsid:ipni.org:names:1234-2";
		String actual = IdUtil.fqName(input);

		assertEquals(expected, actual);
	}

	@Test
	public void testFqNameOnFqId() {
		String input = "urn:lsid:ipni.org:names:1234-2";
		String expected = "urn:lsid:ipni.org:names:1234-2";
		String actual = IdUtil.fqName(input);

		assertEquals(expected, actual);
	}

	@Test
	public void testFqPublicationOnNonFqId() {
		String input = "1234-2";
		String expected = "urn:lsid:ipni.org:publications:1234-2";
		String actual = IdUtil.fqPublication(input);

		assertEquals(expected, actual);
	}

	@Test
	public void testFqPublicationOnFqId() {
		String input = "urn:lsid:ipni.org:publications:1234-2";
		String expected = "urn:lsid:ipni.org:publications:1234-2";
		String actual = IdUtil.fqPublication(input);

		assertEquals(expected, actual);
	}

	@Test
	public void testIsAuthorWithValidAuthor() {
		assertTrue(IdUtil.isAuthorId("urn:lsid:ipni.org:authors:1234-2"));
	}

	@Test
	public void testIsAuthorWithNonAuthor() {
		assertFalse(IdUtil.isAuthorId("urn:lsid:ipni.org:names:1234-2"));
	}

	@Test
	public void testIsNameWithValidName() {
		assertTrue(IdUtil.isNameId("urn:lsid:ipni.org:names:1234-2"));
	}

	@Test
	public void testIsNameWithNonName() {
		assertFalse(IdUtil.isNameId("urn:lsid:ipni.org:publications:1234-2"));
	}

	@Test
	public void testIsPublicationWithValidPublication() {
		assertTrue(IdUtil.isPublicationId("urn:lsid:ipni.org:publications:1234-2"));
	}

	@Test
	public void testIsPublicationWithNonPublication() {
		assertFalse(IdUtil.isPublicationId("urn:lsid:ipni.org:names:1234-2"));
	}
}
