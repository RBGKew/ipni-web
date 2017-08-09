package org.ipni.model;

import org.apache.solr.common.SolrDocument;
import org.ipni.constants.FieldMapping;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Publication {

	private String abbreviation;
	private String bphNumber;
	private String date;
	private String id;
	private String isbn;
	private String issn;
	private String lcNumber;
	private String precededBy;
	private String recordType;
	private String remarks;
	private String supercededBy;
	private String tdwgAbbreviation;
	private String title;
	private String tl2Author;
	private String tl2Number;
	private String url;
	private String version;

	public Publication(SolrDocument publication) {
		this.abbreviation = (String) publication.get(FieldMapping.abbreviation.solrField());
		this.bphNumber = (String) publication.get(FieldMapping.bphNumber.solrField());
		this.date = (String) publication.get(FieldMapping.date.solrField());
		this.id = (String) publication.getFirstValue("id");
		this.isbn = (String) publication.get(FieldMapping.isbn.solrField());
		this.issn = (String) publication.get(FieldMapping.issn.solrField());
		this.lcNumber = (String) publication.get(FieldMapping.lcNumber.solrField());
		this.precededBy = (String) publication.get(FieldMapping.precededBy.solrField());
		this.recordType = "publication";
		this.remarks = (String) publication.get(FieldMapping.remarks.solrField());
		this.supercededBy = (String) publication.get(FieldMapping.supercededBy.solrField());
		this.tdwgAbbreviation = (String) publication.get(FieldMapping.tdwgAbbreviation.solrField());
		this.title = (String) publication.get(FieldMapping.title.solrField());
		this.tl2Author = (String) publication.get(FieldMapping.tl2Author.solrField());
		this.tl2Number = (String) publication.get(FieldMapping.tl2Number.solrField());
		this.url = "/" + id;
		this.version = (String) publication.get(FieldMapping.version.solrField());
	}
}
