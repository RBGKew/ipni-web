package org.ipni.constants;

import java.util.List;

import com.google.common.collect.ImmutableList;

public enum FieldMapping {

	all("all", "*"),
	created("added", "date_created_date"),
	ipniRecordType("ipni_record_type","ipni_record_type_s_lower"),
	updated("modified", "date_last_modified_date"),
	version("version", "version_s_lower"),

	// Citation (Name) fields
	author("name author", "authors_t"),
	authorTeamIds("author team ids", "detail_author_team_ids"),
	bibliographicReference("bibliographic reference", "bibliographic_reference_s_lower"),
	bibliographicTypeInfo("bibliographic type info", "bibliographic_type_info_s_lower"),
	citationType("citation type", "citation_type_s_lower"),
	collectionNumber("collection number", "collection_number_s_lower"),
	collectorTeam("collector team", "collector_team_as_text_t"),
	collectionDay1("collection_day_1", "collection_day_1_s_lower"),
	collectionMonth1("collection_month_1", "collection_month_1_s_lower"),
	collectionYear1("collection_year_1", "collection_year_1_s_lower"),
	collectionDay2("collection_day_2", "collection_day_2_s_lower"),
	collectionMonth2("collection_month_2", "collection_month_2_s_lower"),
	collectionYear2("collection_year_2", "collection_year_2_s_lower"),
	distribution("distribution", "distribution_s_lower"),
	eastOrWest("east or west", "east_or_west_s_lower"),
	endPage("end page", "end_page_s_lower"),
	family("family", "family_s_lower"),
	fullName("full name", "full_name_s_lower"),
	genus("genus", "genus_s_lower"),
	geographicUnit("geographic unit", "geographic_unit_s_lower"),
	hybrid("hybrid", "hybrid_b"),
	hybridGenus("hybrid genus", "hybrid_genus_b"),
	inPowo("in powo", "powo_b"),
	infrafamily("infrafamily", "infra_family_s_lower"),
	infragenus("infragenus", "infra_genus_s_lower"),
	infraspecies("infraspecies", "infraspecies_s_lower"),
	latitudeDegrees("latitude degrees", "latitude_degrees_s_lower"),
	latitudeMinutes("latitude minutes", "latitude_minutes_s_lower"),
	latitudeSeconds("latitude seconds", "latitude_seconds_s_lower"),
	locality("locality", "locality_s_lower"),
	longitudeDegrees("longitude degrees", "longitude_degrees_s_lower"),
	longitudeMinutes("longitude minutes", "longitude_minutes_s_lower"),
	longitudeSeconds("longitude seconds", "longitude_seconds_s_lower"),
	nameStatus("name status", "name_status_s_lower"),
	nameStatusBotCodeType("name status bot code", "name_status_bot_code_type_s_lower"),
	nameStatusEditorComment("name status comment", "name_status_editor_comment_s_lower"),
	nameStatusEditorType("name status type", "name_status_editor_type_s_lower"),
	nameStatusQualifier("name status qualifier", "name_status_qualifier_s_lower"),
	northOrSouth("north or south", "north_or_south_s_lower"),
	originalBasionym("original basionym", "original_basionym_s_lower"),
	originalBasionymAuthorTeam("original basionym author team", "original_basionym_author_team_s_lower"),
	originalCitedType("original cited type", "original_cited_type_s_lower"),
	originalHybridParentage("original hybrid parentage", "original_hybrid_parentage_s_lower"),
	originalParentCitationTaxonNameAuthorTeam("original parent citation taxon name author team", "original_parent_citation_taxon_name_author_team_s_lower"),
	originalRemarks("original remarks", "original_remarks_s_lower"),
	originalReplacedSynonym("original replaced synonym", "original_replaced_synonym_s_lower"),
	originalReplacedSynonymAuthorTeam("original replaced synonym author team", "original_replaced_synonym_author_team_s_lower"),
	originalTaxonDistribution("original taxon distribution", "original_taxon_distribution_s_lower"),
	originalTaxonName("original taxon name", "original_taxon_name_s_lower"),
	originalTaxonNameAuthorTeam("original taxon name author team", "original_taxon_name_author_team_s_lower"),
	otherLinks("other links", "other_links_s_lower"),
	pageAsText("bhl page", "page_as_text_s_lower"),
	primaryPagination("primary pagination", "primary_pagination_s_lower"),
	publication("published in", "publication_s_lower"),
	publicationAuthor("publication author", "publication_author_s_lower"),
	publicationId("published in id", "lookup_publication_id"),
	publicationYearFull("publication year full", "publication_year_full_s_lower"),
	publicationYearNote("publication year note", "publication_year_note_s_lower"),
	publicationYearText("publication year text", "publication_year_text_s_lower"),
	publishingAuthor("publishing author", "publishing_author_s_lower"),
	rank("rank", "rank_s_alphanum"),
	reference("reference", "reference_t"),
	referenceCollation("reference collation", "reference_collation_s_lower"),
	referenceRemarks("reference remarks", "reference_remarks_s_lower"),
	remarks("remarks", "remarks_s_lower"),
	scientificName("scientific name", "taxon_scientific_name_s_lower"),
	secondaryPagination("secondary pagination", "secondary_pagination_s_lower"),
	species("species", "species_s_lower"),
	speciesAuthor("species author", "species_author_s_lower"),
	speciesAuthorTeamIds("species author team ids", "detail_species_author_team_ids"),
	standardisedBasionymAuthorFlag("standardised basionym author flag", "standardised_basionym_author_flag_s_lower"),
	standardisedPublicationFlag("standardised publication flag", "standardised_publication_flag_s_lower"),
	standardisedPublishingAuthorFlag("standardised publishing author flag", "standardised_publishing_author_flag_s_lower"),
	startPage("start page", "start_page_s_lower"),
	suppressed("suppressed", "suppressed_b"),
	topCopy("top copy", "top_copy_b"),
	typeLocations("type locations", "type_locations_s_lower"),
	typeName("type name", "type_name_s_lower"),
	typeRemarks("type remarks", "type_remarks_s_lower"),
	volume("volume", "volume_s_lower"),
	yearPublished("published", "publication_year_i"),

	// Author fields
	authorAlternativeAbbreviations("alternative abbreviations","alternative_abbreviations_s_lower"),
	authorAlternativeNames("detail alternative names","detail_alternative_names"),
	authorComments("comments","comments_s_lower"),
	authorDates("dates","dates_s_lower"),
	authorExampleOfNamePublished("example of name published","example_of_name_published_s_lower"),
	authorForename("author forename","author_forename_s_lower"),
	authorIsoCountries("author country","detail_author_iso_countries"),
	authorName("author name","author_name_s_lower"),
	authorNameNotes("name notes","name_notes_s_lower"),
	authorNameSource("name source","name_source_s_lower"),
	authorStandardForm("author std","standard_form_s_lower"),
	authorSurname("author surname","author_surname_s_lower"),
	authorTaxonGroups("detail taxon groups flat","detail_taxon_groups_flat"),
	authorDateType("author date type", "date_type_string_s_lower"),

	// Publication fieldsabbreviation("abbreviation","abbreviation_s_lower"),
	abbreviation("publication std","abbreviation_s_lower"),
	bphNumber("bph number","bph_number_s_lower"),
	date("date","date_s_lower"),
	isbn("isbn","isbn_s_lower"),
	issn("issn","issn_s_lower"),
	lcNumber("lc number","lc_number_s_lower"),
	precededBy("preceded by","preceded_by_s_lower"),
	supercededBy("superceded by","superceded_by_s_lower"),
	tdwgAbbreviation("tdwg abbreviation","tdwg_abbreviation_s_lower"),
	title("publication title","title_s_lower"),
	tl2Author("tl2 author","tl2_author_s_lower"),
	tl2Number("tl2 number","tl2_number_s_lower")
	;

	public static final List<FieldMapping> citationFields = ImmutableList.<FieldMapping>of(
			fullName,
			author,
			scientificName,
			family,
			infrafamily,
			genus,
			infragenus,
			species,
			infraspecies,
			hybrid,
			hybridGenus,
			rank,
			reference,
			yearPublished,
			publishingAuthor,
			publicationId,
			authorTeamIds,
			speciesAuthorTeamIds,
			originalRemarks,
			pageAsText,
			publication,
			bibliographicReference,
			bibliographicTypeInfo,
			citationType,
			referenceCollation,
			collectionNumber,
			collectorTeam,
			distribution,
			endPage,
			geographicUnit,
			inPowo,
			latitudeDegrees,
			latitudeMinutes,
			latitudeSeconds,
			locality,
			longitudeDegrees,
			longitudeMinutes,
			longitudeSeconds,
			eastOrWest,
			northOrSouth,
			nameStatus,
			nameStatusBotCodeType,
			nameStatusEditorComment,
			nameStatusEditorType,
			nameStatusQualifier,
			originalBasionym,
			originalBasionymAuthorTeam,
			originalCitedType,
			originalHybridParentage,
			originalParentCitationTaxonNameAuthorTeam,
			originalReplacedSynonym,
			originalReplacedSynonymAuthorTeam,
			originalTaxonDistribution,
			originalTaxonName,
			originalTaxonNameAuthorTeam,
			otherLinks,
			primaryPagination,
			publicationAuthor,
			publicationYearFull,
			publicationYearNote,
			publicationYearText,
			referenceRemarks,
			remarks,
			secondaryPagination,
			speciesAuthor,
			standardisedBasionymAuthorFlag,
			standardisedPublicationFlag,
			standardisedPublishingAuthorFlag,
			startPage,
			suppressed,
			topCopy,
			typeLocations,
			typeName,
			typeRemarks,
			volume);

	public static final List<FieldMapping> authorFields = ImmutableList.<FieldMapping>of(
			authorAlternativeAbbreviations,
			authorAlternativeNames,
			authorComments,
			authorDates,
			authorExampleOfNamePublished,
			authorForename,
			authorIsoCountries,
			authorName,
			authorNameNotes,
			authorNameSource,
			authorStandardForm,
			authorSurname,
			authorTaxonGroups);

	public static final List<FieldMapping> publicationFields = ImmutableList.<FieldMapping>of(
			abbreviation,
			bphNumber,
			date,
			isbn,
			issn,
			lcNumber,
			precededBy,
			supercededBy,
			tdwgAbbreviation,
			title,
			tl2Author,
			tl2Number);

	private String solrField;
	private String apiField;

	private FieldMapping(final String apiField, final String solrField) {
		this.solrField = solrField;
		this.apiField = apiField;
	}

	public String solrField() {
		return solrField;
	}

	public String apiField() {
		return apiField;
	}

	public static boolean isValidField(String field) {
		for(FieldMapping f : values()) {
			if(f.apiField.equals(field)) {
				return true;
			}
		}

		return false;
	}

	public static String getSolrFieldFrom(String apiField) {
		for(FieldMapping f : values()) {
			if(f.apiField.equals(apiField)) {
				return f.solrField;
			}
		}

		return null;
	}

	public static String getApiFieldFrom(String solrField) {
		for(FieldMapping f : values()) {
			if(f.solrField.equals(solrField)) {
				return f.apiField;
			}
		}

		return null;
	}
}
