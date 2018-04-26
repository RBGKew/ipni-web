package org.ipni.legacy.model;

import lombok.Getter;

public enum DelimitedAuthorField implements DelimitedField {
	Id("Id", "id"),
	Version("Version", "version"),
	AlternativeAbbreviations("Alternative abbreviations", "alternativeAbbreviations"),
	AlternativeNames("Alternative names", "alternativeNames"),
	DefaultAuthorName("Default author name", ""),
	DefaultAuthorForename("Default author forename", "forename"),
	DefaultAuthorSurname("Default author surname", "surname"),
	StandardForm("Standard form", "standardForm"),
	NameNotes("Name Notes", "notes"),
	NameSource("Name source", "source"),
	Dates("Dates", "dates"),
	DateTypeCode("Date type code", ""),
	DateTypeString("Date type string", ""),
	TaxonGroups("Taxon groups", "taxonGroups"),
	ExamplesOfNamePublished("Example of name published", "examples");

	@Getter private String display;
	@Getter private String field;

	private DelimitedAuthorField(String display, String field) {
		this.display = display;
		this.field = field;
	}
}
