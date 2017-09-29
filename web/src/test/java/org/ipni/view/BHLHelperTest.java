package org.ipni.view;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.ipni.model.BHLLink;
import org.ipni.model.Collation;
import org.ipni.model.Publication;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class BHLHelperTest {

	@Test
	public void extractPageIds() throws IOException {
		String input = "[BHL_page_id:123] [BHL_page_id:456]";
		List<String> expected = ImmutableList.<String>of("123", "456");
		List<String> actual = BHLHelper.extractPageIds(input);

		assertEquals(expected, actual);
	}

	@Test
	public void extractPageIdsHandlesMissingIds() throws IOException {
		String input = "blargedy blarg";
		List<String> expected = null;
		List<String> actual = BHLHelper.extractPageIds(input);

		assertEquals(expected, actual);
	}

	@Test
	public void extractTitleIds() throws IOException {
		String input = "[BHL_title_id:123] [BHL_title_id:456]";
		List<String> expected = ImmutableList.<String>of("123", "456");
		List<String> actual = BHLHelper.extractTitleIds(input);

		assertEquals(expected, actual);
	}

	@Test
	public void extractTitleIdsHandlesMissingIds() throws IOException {
		String input = "blargedy blarg";
		List<String> expected = null;
		List<String> actual = BHLHelper.extractTitleIds(input);

		assertEquals(expected, actual);
	}

	@Test
	public void buildPublicationTitleLinks() throws IOException {
		List<String> input = ImmutableList.<String>of("123");
		List<String> expected = ImmutableList.<String>of(new BHLLink().withTitleId("123").build());
		List<String> actual = BHLHelper.buildPublicationTitleLinks(input);

		assertEquals(expected, actual);
	}

	@Test
	public void buildPublicationPageLinks() throws IOException {
		List<String> input = ImmutableList.<String>of("123");
		List<String> expected = ImmutableList.<String>of(new BHLLink().withPageId("123").build());
		List<String> actual = BHLHelper.buildPublicationPageLinks(input);

		assertEquals(expected, actual);
	}

	@Test
	public void buildNameLinkWithBHLPageId() throws IOException {
		Publication publication = Publication.builder().bhlPageIds(ImmutableList.<String>of("123")).build();
		Collation collation = new Collation("1(2): 3");
		Integer year = 2000;

		String expected = new BHLLink().withPageId("123").withCollation(collation).withYear(year).build();
		String actual = BHLHelper.buildNameLink(publication, collation, year);

		assertEquals(actual, expected);
	}

	@Test
	public void nameLinkWithMoreThanOneBHLPageIdShouldReturnNull() {
		Publication publication = Publication.builder().bhlPageIds(ImmutableList.<String>of("123", "456")).build();
		Collation collation = new Collation("1(2): 3");
		Integer year = 2000;

		String expected = null;
		String actual = BHLHelper.buildNameLink(publication, collation, year);

		assertEquals(actual, expected);
	}

	@Test
	public void nameLinkWithNoBHLIdsShoulReturnNull() {
		Publication publication = Publication.builder().build();
		Collation collation = new Collation("1(2): 3");
		Integer year = 2001;

		String expected = null;
		String actual = BHLHelper.buildNameLink(publication, collation, year);

		assertEquals(actual, expected);
	}

	@Test
	public void buildNameLinkWithBHLTitleId() throws IOException {
		Publication publication = Publication.builder().bhlTitleIds(ImmutableList.<String>of("123")).build();
		Collation collation = new Collation("1(2): 3");
		Integer year = 2000;

		String expected = new BHLLink().withTitleId("123").withCollation(collation).withYear(year).build();
		String actual = BHLHelper.buildNameLink(publication, collation, year);

		assertEquals(actual, expected);
	}

	@Test
	public void nameLinkWithMoreThanOneBHLTitleIdShouldReturnNull() {
		Publication publication = Publication.builder().bhlTitleIds(ImmutableList.<String>of("123", "456")).build();
		Collation collation = new Collation("1(2): 3");
		Integer year = 2000;

		String expected = null;
		String actual = BHLHelper.buildNameLink(publication, collation, year);

		assertEquals(actual, expected);
	}

	@Test
	public void stripBHLMarkers() throws IOException {
		String input = "blah [BHL_page_id:1232] blah [BHL_title_id:4321] blah";
		String expected = "blah  blah  blah";
		String actual = BHLHelper.stripBhlMarkers(input);

		assertEquals(actual, expected);
	}
}
