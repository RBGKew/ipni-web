package org.ipni.indexer.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.Collections;

import org.apache.solr.common.SolrInputDocument;
import org.ipni.indexer.mapper.NamesPublished;
import org.ipni.indexer.mapper.RecordActivity;
import org.ipni.indexer.mapper.Standardization;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatsLoader {

	File published;
	File activity;
	File standardization;

	public StatsLoader(File statsPublishedFile, File statsActivityFile, File statsStandardizationFile) {
		this.published = statsPublishedFile;
		this.activity = statsActivityFile;
		this.standardization = statsStandardizationFile;
	}

	public Collection<SolrInputDocument> getNamesPublishedStats() {
		try {
			NamesPublished np = new NamesPublished(new BufferedReader(new FileReader(published)));
			return np.build();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
			return Collections.emptyList();
		}
	}

	public Collection<SolrInputDocument> getRecordActivityStats() {
		RecordActivity ra;
		try {
			ra = new RecordActivity(new BufferedReader(new FileReader(activity)));
			return ra.build();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
			return Collections.emptyList();
		}
	}

	public SolrInputDocument getStandardizationStats() {
		Standardization std;
		try {
			std = new Standardization(new BufferedReader(new FileReader(standardization)));
			return std.build();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
			return new SolrInputDocument();
		}
	}
}
