package org.ipni.search;

import static org.junit.Assert.assertEquals;

import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Test;

public class IDQueryTest {

	IDQuery idq = new IDQuery();

	@Test
	public void testFqNameId() {
		SolrQuery q = new SolrQuery();
		idq.addQueryOption("name id", "urn:lsid:ipni.org:names:1100324-2", q);

		assertEquals("id:\"urn:lsid:ipni.org:names:1100324-2\"", q.getQuery());
	}

	@Test
	public void testNonFqNameId() {
		SolrQuery q = new SolrQuery();
		idq.addQueryOption("name id", "1100324-2", q);

		assertEquals("id:\"urn:lsid:ipni.org:names:1100324-2\"", q.getQuery());
	}

	@Test
	public void testFqAuthorId() {
		SolrQuery q = new SolrQuery();
		idq.addQueryOption("author id", "urn:lsid:ipni.org:authors:12653-1", q);

		assertEquals("id:\"urn:lsid:ipni.org:authors:12653-1\"", q.getQuery());
	}

	@Test
	public void testNonFqAuthorId() {
		SolrQuery q = new SolrQuery();
		idq.addQueryOption("author id", "12653-1", q);

		assertEquals("id:\"urn:lsid:ipni.org:authors:12653-1\"", q.getQuery());
	}

	@Test
	public void testFqPublicationId() {
		SolrQuery q = new SolrQuery();
		idq.addQueryOption("publication id", "urn:lsid:ipni.org:publications:12653-1", q);

		assertEquals("id:\"urn:lsid:ipni.org:publications:12653-1\"", q.getQuery());
	}

	@Test
	public void testNonFqPublicationId() {
		SolrQuery q = new SolrQuery();
		idq.addQueryOption("publication id", "12653-1", q);

		assertEquals("id:\"urn:lsid:ipni.org:publications:12653-1\"", q.getQuery());
	}

	@Test
	public void testPublishedInFqId() {
		SolrQuery q = new SolrQuery();
		idq.addQueryOption("published in id", "urn:lsid:ipni.org:publications:1071-2", q);

		assertEquals("lookup_publication_id:\"urn:lsid:ipni.org:publications:1071-2\"", q.getQuery());
	}

	@Test
	public void testPublishedInNonFqId() {
		SolrQuery q = new SolrQuery();
		idq.addQueryOption("published in id", "1071-2", q);

		assertEquals("lookup_publication_id:\"urn:lsid:ipni.org:publications:1071-2\"", q.getQuery());
	}

	@Test
	public void testAuthorTeamFqId() {
		SolrQuery q = new SolrQuery();
		idq.addQueryOption("author team ids", "urn:lsid:ipni.org:authors:12653-1", q);

		assertEquals("detail_author_team_ids:*@12653-1@*aut*", q.getQuery());
	}
}
