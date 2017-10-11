package org.ipni.view;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.ipni.model.BHLLink;
import org.ipni.model.Collation;
import org.ipni.model.Name;
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
	public void dontBuildNameLinkWithPublicationPageId() throws IOException {
		Publication publication = Publication.builder().bhlPageIds(ImmutableList.<String>of("123")).build();
		Name name = Name.builder()
				.collation(new Collation("1(2): 3"))
				.publicationYear(2000)
				.build();

		String expected = null;
		String actual = BHLHelper.buildNameLink(name, publication);

		assertEquals(actual, expected);
	}

	@Test
	public void nameLinkWithNoPublicationBHLIdsShoulReturnNull() {
		Publication publication = Publication.builder().build();
		Name name = Name.builder()
				.collation(new Collation("1(2): 3"))
				.publicationYear(2000)
				.build();

		String expected = null;
		String actual = BHLHelper.buildNameLink(name, publication);

		assertEquals(actual, expected);
	}

	@Test
	public void buildNameLinkWithPublicationTitleId() throws IOException {
		Publication publication = Publication.builder().bhlTitleIds(ImmutableList.<String>of("123")).build();
		Name name = Name.builder()
				.collation(new Collation("1(2): 3"))
				.publicationYear(2000)
				.build();

		String expected = new BHLLink().withTitleId("123").withCollation(name.getCollation()).withYear(name.getPublicationYear()).build();
		String actual = BHLHelper.buildNameLink(name, publication);

		assertEquals(actual, expected);
	}

	@Test
	public void nameLinkWithMoreThanOneBHLTitleIdShouldReturnNull() {
		Publication publication = Publication.builder().bhlTitleIds(ImmutableList.<String>of("123", "456")).build();
		Name name = Name.builder()
				.collation(new Collation("1(2): 3"))
				.publicationYear(2000)
				.build();

		String expected = null;
		String actual = BHLHelper.buildNameLink(name, publication);

		assertEquals(actual, expected);
	}

	@Test
	public void nameWithPageAsTextLinksDirectlyToBHLPage() {
		Name name = Name.builder().pageAsText("1234").build();

		String expected = new BHLLink().withPageId("1234").build();
		String actual = BHLHelper.buildNameLink(name, null);

		assertEquals(actual, expected);
	}

	@Test
	public void nameWithPageAsTextLinksDirectlyToBHLPageAndIgnoresAnyPublicationIds() {
		Publication publication = Publication.builder().bhlTitleIds(ImmutableList.<String>of("123", "456")).build();
		Name name = Name.builder().pageAsText("1234").collation(new Collation("1(2): 3")).build();

		String expected = new BHLLink().withPageId("1234").build();
		String actual = BHLHelper.buildNameLink(name, publication);

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
