package org.ipni.search;

import static org.junit.Assert.assertEquals;

import org.apache.solr.client.solrj.SolrQuery;
import org.ipni.constants.FieldMapping;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

public class MultiFieldQueryTest {

	private MultiFieldQuery mfq;
	private SolrQuery q;

	@Before
	public void setup() {
		this.mfq = new MultiFieldQuery(ImmutableSet.<String>of(
				FieldMapping.family.solrField(),
				FieldMapping.species.solrField()));
		this.q = new SolrQuery();
	}

	@Test
	public void testNewMultiFieldQuery() {
		mfq.addQueryOption("", "Poa", q);
		assertEquals("(family_s_lower:Poa OR species_s_lower:Poa)", q.getQuery());
	}

	@Test
	public void testNewMultiFieldQueryWithWildcard() {
		mfq.addQueryOption("", "Poa an*", q);
		assertEquals("{!complexphrase inOrder true}(family_s_lower:\"Poa an*\""
				+ " OR species_s_lower:\"Poa an*\")",
				q.getQuery());
	}

	@Test
	public void testMultiFieldQueryWithExistingQuery() {
		q.setQuery("a:blarg");
		mfq.addQueryOption("", "Poa", q);
		assertEquals("a:blarg AND (family_s_lower:Poa OR species_s_lower:Poa)", q.getQuery());
	}

	@Test
	public void testMultiFieldQueryWithExistingQueryAndWildcard() {
		q.setQuery("a:blarg");
		mfq.addQueryOption("", "Poa an*", q);
		assertEquals("a:blarg AND {!complexphrase inOrder true}(family_s_lower:\"Poa an*\""
				+ " OR species_s_lower:\"Poa an*\")", q.getQuery());
	}
}
