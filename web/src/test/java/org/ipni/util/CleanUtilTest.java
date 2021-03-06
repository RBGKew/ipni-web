package org.ipni.util;

import static org.ipni.util.CleanUtil.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CleanUtilTest {

	@Test
	public void testEqualsPrefixedToNull() {
		assertEquals(equalsPrefixedToNull(null), null);
		assertEquals(equalsPrefixedToNull("= blah blah blah"), null);
		assertEquals(equalsPrefixedToNull("blah blah blah"), "blah blah blah");
	}

	@Test
	public void testStripBrackets() {
		assertEquals(stripBrackets(null), null);
		assertEquals(stripBrackets(
				"[Dec 1992 publ. 12 Jan 1993]"),
				"Dec 1992 publ. 12 Jan 1993");
		assertEquals(stripBrackets(
				"Dec 1992 publ. 12 Jan 1993"),
				"Dec 1992 publ. 12 Jan 1993");
	}

	@Test
	public void testStripLeadingPunctuation() {
		assertEquals(stripLeadingPunctuation(null), null);
		assertEquals(stripLeadingPunctuation(
				";  Cheval. Monogr. Myricacées, [107]. 1901"),
				"Cheval. Monogr. Myricacées, [107]. 1901");
		assertEquals(stripLeadingPunctuation(
				", [Rar. Lig. [Ital.] Pl. Decas 1]"),
				"[Rar. Lig. [Ital.] Pl. Decas 1]");
		assertEquals(stripLeadingPunctuation(
				", [Rar. Lig. [Ital.] Pl. Decas 1]"),
				"[Rar. Lig. [Ital.] Pl. Decas 1]");
		assertEquals(stripLeadingPunctuation(
				"=  Verhandelingen, Afdeling Natuurkunde ser. 2, 59] (1970) 251."),
				"Verhandelingen, Afdeling Natuurkunde ser. 2, 59] (1970) 251.");
		assertEquals(stripLeadingPunctuation(
				"Verhandelingen, Afdeling Natuurkunde ser. 2, 59] (1970) 251."),
				"Verhandelingen, Afdeling Natuurkunde ser. 2, 59] (1970) 251.");
	}

	@Test
	public void testStripEnglishInBrackets() {
		assertEquals(null, stripEnglishInBrackets(null));
		assertEquals(null, stripEnglishInBrackets(" [english] "));
		assertEquals("blah blah", stripEnglishInBrackets("[english] blah blah"));
		assertEquals("blah blah", stripEnglishInBrackets("blah blah [english]"));
		assertEquals("blah blah blah", stripEnglishInBrackets("blah blah [english] blah"));
		assertEquals("[blah blah] english", stripEnglishInBrackets("[blah blah] english"));
	}
}