package org.ipni.model;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

public class CollationTest {

	class Testcase {
		public String collation;
		public Optional<String> volume = Optional.empty();
		public Optional<String> issue = Optional.empty();
		public Optional<String> page = Optional.empty();

		public Testcase(String collation) {
			this.collation = collation;
		}

		public Testcase hasVolume(String volume) {
			this.volume = Optional.of(volume);
			return this;
		}

		public Testcase andIssue(String issue) {
			this.issue = Optional.of(issue);
			return this;
		}

		public Testcase hasPage(String page) {
			this.page = Optional.of(page);
			return this;
		}

		public Testcase andPage(String page) {
			return hasPage(page);
		}
	}

	Testcase[] tests = new Testcase[] {
			new Testcase("2: 123").hasVolume("2").andPage("123"),
			new Testcase("2: 123 (-124)").hasVolume("2").andPage("123"),
			new Testcase("2: 123 (2 errata)").hasVolume("2").andPage("123"),
			new Testcase("1: 345; 2: errata").hasVolume("1").andPage("345"),
			new Testcase("1: 157 [no. 10]").hasVolume("1").andPage("157"),
			new Testcase("1: 75 (khkjdshg; jhgzsdf)").hasVolume("1").andPage("75"),
			new Testcase("2: 638 [\"938\"]").hasVolume("2").andPage("638"),
			new Testcase("2: 652 (errata)").hasVolume("2").andPage("652"),
			new Testcase("2: 828, errata [1231]").hasVolume("2").andPage("828"),
			new Testcase("2: 828. errata [1231]").hasVolume("2").andPage("828"),
			new Testcase("vol 2: 927, 1199, [1230]").hasVolume("2").andPage("927"),
			new Testcase(" 2: 927, 1199, [add. post indicem]").hasVolume("2").andPage("927"),
			new Testcase("2(1): 123").hasVolume("2").andIssue("1").andPage("123"),
			new Testcase("2(1): 123 (-124)").hasVolume("2").andIssue("1").andPage("123"),
			new Testcase("2(1): 123 (2 errata)").hasVolume("2").andIssue("1").andPage("123"),
			new Testcase("1(1): 345; 2: errata").hasVolume("1").andIssue("1").andPage("345"),
			new Testcase("1(1): 157 [no. 10]").hasVolume("1").andIssue("1").andPage("157"),
			new Testcase("1(1): 75 (khkjdshg; jhgzsdf)").hasVolume("1").andIssue("1").andPage("75"),
			new Testcase("2(1): 638 [\"938\"]").hasVolume("2").andIssue("1").andPage("638"),
			new Testcase("2(1): 652 (errata)").hasVolume("2").andIssue("1").andPage("652"),
			new Testcase("2(1): 828, errata [1231]").hasVolume("2").andIssue("1").andPage("828"),
			new Testcase("vol 2(1): 927, 1199, [1230]").hasVolume("2").andIssue("1").andPage("927"),
			new Testcase(" 2(1): 927, 1199, [add. post indicem]").hasVolume("2").andIssue("1").andPage("927"),
			new Testcase(" 2(1-2): 927").hasVolume("2").andIssue("1-2").andPage("927"),
			new Testcase(" 2(1.2): 927").hasVolume("2").andIssue("1.2").andPage("927"),
			new Testcase("1").hasPage("1"),
			new Testcase("1.").hasPage("1"),

			// non-parseable (for now) collations should just return null everywhere
			new Testcase("4, Fam. 117 I: 274, fig. 65"),
			new Testcase("(1892) 133."),
			new Testcase("iii. 25"),
			new Testcase("lxxxix. 396 (1928)"),
			new Testcase("xi. (1876) 125."),
			new Testcase(null),
	};

	@Test
	public void testCollationParsing() {
		for(Testcase expected : tests) {
			Collation actual = new Collation(expected.collation);

			assertEquals("in \"" + expected.collation + "\"", expected.volume, actual.getVolume());
			assertEquals("in \"" + expected.collation + "\"", expected.issue, actual.getIssue());
			assertEquals("in \"" + expected.collation + "\"", expected.page, actual.getPage());
		}
	}
}
