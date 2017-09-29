package org.ipni.view;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.ipni.model.BHLLink;
import org.ipni.model.Collation;
import org.junit.Test;

public class BHLLinkTest {

	@Test
	public void setsDefaultParams() {
		String expected = "http://www.biodiversitylibrary.org/openurl?ctx_ver=Z39.88-2004&rft_val_fmt=info:ofi/fmt:kev:mtx:book&url_ver=z39.88-2004";
		String actual = new BHLLink().build();

		assertEquals(expected, actual);
	}

	@Test
	public void setsTitleIdParam() {
		String expected = "rft_id=http://www.biodiversitylibrary.org/bibliography/213";
		String actual = new BHLLink().withTitleId("213").build();

		assertThat(actual, containsString(expected));
	}

	@Test
	public void setsPageIdParam() {
		String expected = "rft_id=http://www.biodiversitylibrary.org/page/213";
		String actual = new BHLLink().withPageId("213").build();

		assertThat(actual, containsString(expected));
	}

	@Test
	public void setsVolumeParam() {
		String expected = "rft.volume=1";
		String actual = new BHLLink().withVolume("1").build();

		assertThat(actual, containsString(expected));
	}

	@Test
	public void setsIssueParam() {
		String expected = "rft.issue=2";
		String actual = new BHLLink().withIssue("2").build();

		assertThat(actual, containsString(expected));
	}

	@Test
	public void doesntSetIssueParamWhenIssueHasDash() {
		String notExpected = "rft.issue=2-3";
		String actual = new BHLLink().withIssue("2-3").build();

		assertThat(actual, not(containsString(notExpected)));
	}

	@Test
	public void truncatesIssueWhenDecimal() {
		String expected = "rft.issue=2";
		String actual = new BHLLink().withIssue("2.3").build();

		assertThat(actual, containsString(expected));
	}

	@Test
	public void setsPageParam() {
		String expected = "rft.spage=2";
		String actual = new BHLLink().withPage("2").build();

		assertThat(actual, containsString(expected));
	}

	@Test
	public void setsYearParam() {
		String expected = "rft.date=2000";
		String actual = new BHLLink().withYear(2000).build();

		assertThat(actual, containsString(expected));
	}

	@Test
	public void collationSetsVolumeIssueAndPage() {
		Collation collation = new Collation("1(2): 55");
		String volumePart = "rft.volume=1";
		String issuePart = "rft.issue=2";
		String pagePart = "rft.spage=55";
		String actual = new BHLLink().withCollation(collation).build();

		assertThat(actual, containsString(volumePart));
		assertThat(actual, containsString(issuePart));
		assertThat(actual, containsString(pagePart));
	}

	@Test
	public void addingNullValuesDoesntChangeLink() {
		BHLLink link = new BHLLink();

		assertEquals(link.withPageId(null).build(), link.build());
		assertEquals(link.withTitleId(null).build(), link.build());
		assertEquals(link.withVolume(null).build(), link.build());
		assertEquals(link.withIssue(null).build(), link.build());
		assertEquals(link.withPage(null).build(), link.build());
		assertEquals(link.withYear(null).build(), link.build());
		assertEquals(link.withCollation(null).build(), link.build());
	}
}