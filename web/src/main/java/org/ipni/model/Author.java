package org.ipni.model;

import org.apache.solr.common.SolrDocument;
import org.ipni.constants.FieldMapping;
import org.ipni.util.IdUtil;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.base.Joiner;

import org.ipni.view.BHLHelper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
	private String fqId;
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
		this.alternativeNames = formatAlternateNames(author.getFieldValues(FieldMapping.authorAlternativeNames.solrField()));
		String rawComments = (String) author.get(FieldMapping.authorComments.solrField());
		this.comments = BHLHelper.stripBhlMarkers(rawComments);
		this.dates = (String) author.get(FieldMapping.authorDates.solrField());
		this.examples = (String) author.get(FieldMapping.authorExampleOfNamePublished.solrField());
		this.forename = (String) author.get(FieldMapping.authorForename.solrField());
		this.fqId = (String) author.getFirstValue("id");
		this.id = IdUtil.idPart(fqId);
		this.isoCountries =  join(author.getFieldValues(FieldMapping.authorIsoCountries.solrField()));
		this.notes = (String) author.get(FieldMapping.authorNameNotes.solrField());
		this.recordType = "author";
		this.source = (String) author.get(FieldMapping.authorNameSource.solrField());
		this.standardForm = (String) author.get(FieldMapping.authorStandardForm.solrField());
		this.surname = (String) author.get(FieldMapping.authorSurname.solrField());
		this.taxonGroups = join(author.getFieldValues(FieldMapping.authorTaxonGroups.solrField()));
		this.url = "/a/" + id;
		this.version = (String) author.get(FieldMapping.version.solrField());
		this.bhlPageIds = BHLHelper.extractPageIds(rawComments);
	}

	private String formatAlternateNames(Collection<Object> fieldValues) {
		if(fieldValues == null) return null;

		return fieldValues.stream()
				.map(Object::toString)
				.filter(val -> !val.startsWith(">"))
				.collect(Collectors.joining(", "));
	}

	private String join(Collection<Object> fieldValues) {
		if(fieldValues == null) return null;

		return Joiner.on(", ").join(fieldValues);
	}

	@JsonProperty
	public boolean hasBhlLinks() {
		return bhlPageIds != null;
	}

	public List<String> getBhlPageLinks() {
		return BHLHelper.buildPublicationPageLinks(bhlPageIds);
	}
}
