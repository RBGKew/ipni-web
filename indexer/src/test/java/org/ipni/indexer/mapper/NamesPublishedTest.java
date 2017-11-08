package org.ipni.indexer.mapper;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

public class NamesPublishedTest {

	BufferedReader input;
	Map<String,Map<String, Map<String, Integer>>> map;
	NamesPublished np;

	@Before
	public void setup() throws FileNotFoundException {
		input = new BufferedReader(new FileReader("src/test/resources/ipniWebStatsNamePublishedSample.csv"));
		np = new NamesPublished(input);
		map = np.buildMap();
	}

	@Test
	public void testBuildMap() {
		assertEquals(Integer.valueOf(1), map.get("1985").get("comb. nov*").get("familial"));
		assertEquals(Integer.valueOf(19), map.get("2000").get("comb. nov*").get("infrafamilial"));
		assertEquals(Integer.valueOf(35), map.get("1873").get("tax. nov").get("infrafamilial"));
	}

	@Test
	public void testBuildJson() throws JsonProcessingException {
		assertEquals(
				"{\"tax. nov\":{\"infraspecific\":327,\"specific\":393,\"infrafamilial\":35,\"infrageneric\":38}}",
				np.buildJson(map.get("1873")));
	}
}
