package org.ipni.model;

import org.apache.solr.common.SolrDocument;
import org.ipni.constants.FieldMapping;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.ipni.view.BHLHelper;
import java.util.List;
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
	
	@JsonIgnore
	private List<String> bhlPageIds;


	public Author(SolrDocument author) {
		this.alternativeAbbreviations = (String) author.get(FieldMapping.authorAlternativeAbbreviations.solrField());
		this.alternativeNames = (String) author.getFirstValue(FieldMapping.authorAlternativeNames.solrField());
		String rawComments = (String) author.get(FieldMapping.authorComments.solrField());
		this.comments = BHLHelper.stripBhlMarkers(rawComments);
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
		this.bhlPageIds = BHLHelper.extractPageIds(rawComments);
	}
	
	@JsonProperty
	public boolean hasBhlLinks() {
		return bhlPageIds != null;
	}
	
	public List<String> getBhlPageLinks() {
		return BHLHelper.buildPublicationPageLinks(bhlPageIds);
	}
}
