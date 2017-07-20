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
	private boolean topCopy;
	
	public Record(SolrDocument item){
		author = (String) item.getFirstValue(FieldMapping.author.solrField());
		family = (String) item.get(FieldMapping.family.solrField());
		genus = (String) item.get(FieldMapping.genus.solrField());
		infraspecific = (String) item.get(FieldMapping.infraspecies.solrField());
		name = (String) item.get(FieldMapping.scientificName.solrField());
		publication = (String) item.getFirstValue(FieldMapping.reference.solrField());
		rank = (String) item.get(FieldMapping.rank.solrField());
		species = (String) item.get(FieldMapping.species.solrField());
		topCopy = (Boolean) item.get(FieldMapping.topCopy.solrField());
		url = constructUrl((String) item.get("id"));
	}
	
	private String constructUrl(String id){
		return "/urn:lsid:ipni.org:names:" + id;	
	}

}
