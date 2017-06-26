package org.ipni.response;

import org.apache.solr.common.SolrDocument;
import org.ipni.constants.FieldMapping;
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
		name = (String) item.get(FieldMapping.scientificName.solrField());
		author = (String) item.get(FieldMapping.author.solrField());
		publication = (String) item.getFirstValue(FieldMapping.reference.solrField());
		rank = (String) item.get(FieldMapping.rank.solrField());
		author = (String) item.get(FieldMapping.author.solrField());
		url = constructUrl((String) item.get("id"));
		family = (String) item.get(FieldMapping.family.solrField());
		genus = (String) item.get(FieldMapping.genus.solrField());
		species = (String) item.get(FieldMapping.species.solrField());
		infraspecific = (String) item.get(FieldMapping.infraspecies.solrField());
		
	}
	
	private String constructUrl(String id){
		return "/urn:lsid:ipni.org:names:" + id;	
	}

}
