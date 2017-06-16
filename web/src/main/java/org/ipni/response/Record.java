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
		name = (String) item.get("taxon_scientific_name_s_lower");
		author = (String) item.get("authors_s_lower");
		publication = (String) item.get("reference_s_lower");
		rank = (String) item.get("rank_s_lower");
		url = constructUrl((String) item.get("id"));
		family = (String) item.get("family_s_lower");
		genus = (String) item.get("genus_s_lower");
		species = (String) item.get("species_s_lower");
		infraspecific = (String) item.get("infraspecies_s_lower");
		
	}
	
	private String constructUrl(String id){
		return "/urn:lsid:ipni.org:names:" + id;	
	}
}
