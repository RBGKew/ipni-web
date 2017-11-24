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
		String expected = "[[\"date\",\"standardised author citations\",\"standardised publication titles\",\"plausible publication year\",\"no roman numerals in collation\"],"
				+ "[\"2006-03-01\",\"64.0\",\"35.9\",\"35.9\",\"53.9\"],"
				+ "[\"2006-04-01\",\"64.0\",\"38.7\",\"35.9\",\"55.6\"],"
				+ "[\"2006-05-01\",\"64.7\",\"43.4\",\"36.8\",\"56.4\"]]";
		String actual = std.buildJson(list);

		assertEquals(expected, actual);
	}
}
