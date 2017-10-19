package org.ipni.download;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.ipni.constants.FieldMapping;
import org.ipni.search.QueryBuilder;

public class Downloader {

	private static final int MAX_DOWNLOAD = 10000;

	private Map<String, String> params;

	private Map<String, List<FieldMapping>> exportFields;

	private SolrClient solr;

	private Packager packager;

	public Downloader(Map<String, String> params, Map<String, List<FieldMapping>> exportFields, SolrClient solr) {
		this.params = params;
		this.exportFields = exportFields;
		this.solr = solr;
	}

	public File buildArchive() throws IOException, SolrServerException {
		SolrQuery query = new QueryBuilder(params)
				.addParam("perPage", Integer.toString(MAX_DOWNLOAD))
				.build();

		this.packager = new Packager(solr.query(query).getResults(), exportFields);

		return packager.create();
	}

	public void cleanup() throws IOException {
		packager.cleanup();
	}
}
