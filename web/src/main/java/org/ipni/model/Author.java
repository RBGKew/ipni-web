package org.ipni.model;

import org.apache.solr.common.SolrDocument;
import org.ipni.constants.FieldMapping;
import org.ipni.response.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Author {

	private String alternativeAbbreviations;
	private String alternativeNames;
	private String comments;
	private String dates;
	private String examples;
	private String forename;
	private String id;
	private String isoCountries;
	private String notes;
	private String recordType;
	private String source;
	private String standardForm;
	private String surname;
	private String taxonGroups;
	private String url;
	private String version;
	private Response namesByAuthor;

	public Author(SolrDocument author) {
		this.alternativeAbbreviations = (String) author.get(FieldMapping.authorAlternativeAbbreviations.solrField());
		this.alternativeNames = (String) author.getFirstValue(FieldMapping.authorAlternativeNames.solrField());
		this.comments = (String) author.get(FieldMapping.authorComments.solrField());
		this.dates = (String) author.get(FieldMapping.authorDates.solrField());
		this.examples = (String) author.get(FieldMapping.authorExampleOfNamePublished.solrField());
		this.forename = (String) author.get(FieldMapping.authorForename.solrField());
		this.id = (String) author.getFirstValue("id");
		this.isoCountries =  (String) author.getFirstValue(FieldMapping.authorIsoCountries.solrField());
		this.notes = (String) author.get(FieldMapping.authorNameNotes.solrField());
		this.recordType = "author";
		this.source = (String) author.get(FieldMapping.authorNameSource.solrField());
		this.standardForm = (String) author.get(FieldMapping.authorStandardForm.solrField());
		this.surname = (String) author.get(FieldMapping.authorSurname.solrField());
		this.taxonGroups = (String) author.getFirstValue(FieldMapping.authorTaxonGroups.solrField());
		this.url = "/" + id;
		this.version = (String) author.get(FieldMapping.version.solrField());
	}
}
