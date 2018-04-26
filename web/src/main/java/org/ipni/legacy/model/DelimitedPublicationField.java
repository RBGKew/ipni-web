package org.ipni.legacy.model;

import lombok.Getter;

public enum DelimitedPublicationField implements DelimitedField {
	Id("Id", "id"),
	Version("Version", "version"),
	Abbreviation("Abbreviation", "abbreviation"),
	Title("Title", "title"),
	Remarks("Remarks", "remarks"),
	BPHNumber("BPH number", "bphNumber"),
	ISBN("ISBN", "isbn"),
	ISSN("ISSN", "issn"),
	AuthorsRole("Authors role", ""),
	Edition("Edition", ""),
	InPublicationFacade("In publication facade", ""),
	Date("Date", "date"),
	LCNumber("LC number", "lcNumber"),
	Place("Place", ""),
	PublicationAuthorTeam("Publication author team", ""),
	PrecededBy("Preceded by", "precededBy"),
	TL2Author("TL2 author", "tl2Author"),
	TL2Number("TL2 number", "tl2Number"),
	TDWGAbbreviation("TDWG abbreviation", "");

	@Getter private String display;
	@Getter private String field;

	private DelimitedPublicationField(String display, String field) {
		this.display = display;
		this.field = field;
	}
}
