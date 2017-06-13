package org.ipni.response;

import org.apache.solr.common.SolrDocument;

import lombok.Data;

@Data
public class Record {
	private String name;
	private String author;
	private String publication;
	private String rank;
	private String url;
	
	public Record(SolrDocument item){
		name = (String) item.get("name");
		author = (String) item.get("author");
		publication = (String) item.get("publication");
		rank = (String) item.get("rank");
		url = (String) item.get("url");
		
	}
}
