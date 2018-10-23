package org.ipni.search;

import static org.junit.Assert.assertEquals;

import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Before;
import org.junit.Test;

public class QueryOptionTest {

	class TestQueryOption extends QueryOption {
		@Override
		public void addQueryOption(String key, String value, SolrQuery query) {
			value = prepareValue(key, value);
			key = prepareField(key, value);
			query.setQuery(String.format("%s:%s", key, value));
		}
	}

	private TestQueryOption tqo;
	private SolrQuery q;

	@Before
	public void setup() {
		tqo = new TestQueryOption();
		q = new SolrQuery();
	}

	@Test
	public void testPrepareValueWithSpaces() {
		tqo.addQueryOption("publication title", "World Bamboo and Rattan", q);
		assertEquals("title_s_lower:\"World Bamboo and Rattan\"", q.getQuery());
	}

	@Test
	public void testPrepareValueWithPluses() {
		tqo.addQueryOption("publication title", "World+Bamboo+and+Rattan", q);
		assertEquals("title_s_lower:\"World+Bamboo+and+Rattan\"", q.getQuery());
	}

	@Test
	public void testPrepareValueWithWildcard() {
		tqo.addQueryOption("publication title", "World Bamb*", q);
		assertEquals("{!complexphrase inOrder=true}title_s_lower:\"World Bamb*\"", q.getQuery());
	}

	@Test
	public void testPrepareValueWithFloatingWildcard() {
		tqo.addQueryOption("publication title", "World *", q);
		assertEquals("title_s_lower:World", q.getQuery());
	}

	@Test
	public void testPrepareValueWithOneCharacterPrefixBeforeWildcard() {
		tqo.addQueryOption("publication title", "World B*", q);
		assertEquals("title_s_lower:World", q.getQuery());
	}

	@Test
	public void testPrepareValueWithOneCharacterSuffixBeforeWildcard() {
		tqo.addQueryOption("publication title", "World *o", q);
		assertEquals("title_s_lower:World", q.getQuery());
	}

	@Test
	public void testPrepareValue() {
		tqo.addQueryOption("publication title", "Canadian Journal of Botany. Ot*", q);
		assertEquals("{!complexphrase inOrder=true}title_s_lower:\"Canadian Journal of Botany. Ot*\"", q.getQuery());
	}
}
