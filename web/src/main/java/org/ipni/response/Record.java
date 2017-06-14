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
	private String family;
	private String genus;
	private String species;
	private String infraspecific;
	
	public Record(SolrDocument item){
		name = (String) item.get("genus_s") +  " " + (String) item.get("species_s");
		author = (String) item.get("authors_s");
		publication = (String) item.get("reference_s");
		rank = (String) item.get("rank_s");
		url = constructUrl((String) item.get("id"));
		family = (String) item.get("family_s");
		genus = (String) item.get("genus_s");
		species = (String) item.get("species_s");
		infraspecific = (String) item.get("infraspecies_s");
		
	}
	
	private String constructUrl(String id){
		return "/urn:lsid:ipni.org:names:" + id;	
	}
}
