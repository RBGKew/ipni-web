package org.ipni.indexer.mapper;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Before;
import org.junit.Test;

public class RecordActivityTest {

	BufferedReader input;
	Map<String,Map<String, Map<String, List<Integer>>>> map;
	RecordActivity ra;

	@Before
	public void setup() throws FileNotFoundException {
		input = new BufferedReader(new FileReader("src/test/resources/ipniWebStatsRecordActivitySample.csv"));
		ra = new RecordActivity(input);
		map = ra.buildMap();
	}

	@Test
	public void buildMap() {
		assertEquals(Integer.valueOf(59), map.get("Addition").get("2003").get("Authors").get(6));
		assertEquals(Integer.valueOf(110), map.get("Update").get("2003").get("Authors").get(9));
		assertEquals(Integer.valueOf(148), map.get("Addition").get("2003").get("Name citations").get(7));
		assertEquals(Integer.valueOf(714), map.get("Update").get("2003").get("Name citations").get(6));
		assertEquals(Integer.valueOf(8), map.get("Addition").get("2003").get("Publications").get(6));
		assertEquals(Integer.valueOf(6), map.get("Update").get("2003").get("Publications").get(9));
	}

	@Test
	public void buildJson() {
		assertEquals(
				"{\"Name citations\":[0,0,0,0,0,0,0,148,0,0,0,0],\"Publications\":[0,0,0,0,0,0,8,0,0,0,0,0],\"Authors\":[0,0,0,0,0,0,59,0,0,0,0,0]}",
				ra.buildJson(map.get("Addition").get("2003")));
	}
}
