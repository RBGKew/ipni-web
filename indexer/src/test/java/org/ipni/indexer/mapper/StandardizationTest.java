package org.ipni.indexer.mapper;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StandardizationTest {

	BufferedReader input;
	List<List<String>> list;
	Standardization std ;

	@Before
	public void setup() throws FileNotFoundException {
		input = new BufferedReader(new FileReader("src/test/resources/ipniWebStatsStandardizationSample.csv"));
		std = new Standardization(input);
		list = std.buildList();
	}

	@Test
	public void buildJson() {
		String expected = "[[\"date\",\"2006-03-01\",\"2006-04-01\",\"2006-05-01\"],"
				+ "[\"no roman numerals in collation\",\"53.9\",\"55.6\",\"56.4\"],"
				+ "[\"plausible publication year\",\"35.9\",\"35.9\",\"36.8\"],"
				+ "[\"standardised publication titles\",\"35.9\",\"38.7\",\"43.4\"],"
				+ "[\"standardised author citations\",\"64.0\",\"64.0\",\"64.7\"]]";
		String actual = std.buildJson(list);

		assertEquals(expected, actual);
	}

}
