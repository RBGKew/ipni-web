package org.ipni.constants;

public enum FieldMapping {

	author("author", "authors_t"),
	family("family", "family_s_lower"),
	genus("genus", "genus_s_lower"),
	hybrid("hybrid", "hybrid_s_lower"),
	hybridGenus("hybrid genus", "hybrid_genus_s_lower"),
	infraspecies("infraspecies", "infraspecies_s_lower"),
	rank("rank", "rank_s_lower"),
	reference("reference", "reference_t"),
	scientificName("scientific name", "taxon_scientific_name_s_lower"),
	species("species", "species_s_lower"),
	yearPublished("published", "publication_year_s_lower"),
	publishingAuthor("publishing author", "publishing_author_s_lower"), 
	publicationId("publication id", "publication_id"),
	
	originalRemarks("original remarks", "original_remarks_s_lower"),
	publication("publication", "publication_s_lower"),
	infraspecific("infraspecific", "infraspecific_s_lower"),
	bibliographicReference("bibliographic reference", "bibliographic_reference_s_lower"),
	bibliographicTypeInfo("bibliographic type info", "bibliographic_type_info_s_lower"),
	citationType("citation type", "citation_type_s_lower"),
	referenceCollation("reference collation", "reference_collation_s_lower"),
	collectionNumber("collection number", "collection_number_s_lower"),
	collectorTeam("collector team", "collector_team_s_lower"),
	distribution("distribution", "distribution_s_lower"),
	eastOrWest("east or west", "east_or_west_s_lower"),
	endPage("end page", "end_page_s_lower"),
	fullName("full name", "full_name_s_lower"),
	geographicUnit("geographic unit", "geographic_unit_s_lower"),
	infrafamily("infrafamily", "infrafamily_s_lower"),
	infragenus("infragenus", "infragenus_s_lower"),
	latitudeDegrees("latitude degrees", "latitude_degrees_s_lower"),
	latitudeMinutes("latitude minutes", "latitude_minutes_s_lower"),
	latitudeSeconds("latitude seconds", "latitude_seconds_s_lower"),
	locality("locality", "locality_s_lower"),
	longitudeDegrees("longitude degrees", "longitude_degrees_s_lower"),
	longitudeMinutes("longitude minutes", "longitude_minutes_s_lower"),
	longitudeSeconds("longitude seconds", "longitude_seconds_s_lower"),
	nameStatus("name status", "name_status_s_lower"),
	nameStatusBotCodeType("name status bot code type", "name_status_bot_code_type_s_lower"),
	nameStatusEditorComment("name status editor comment", "name_status_editor_comment_s_lower"),
	nameStatusEditorType("name status editor type", "name_status_editor_type_s_lower"),
	nameStatusQualifier("name status qualifier", "name_status_qualifier_s_lower"),
	northOrSouth("north or south", "north_or_south_s_lower"),
	originalBasionym("original basionym", "original_basionym_s_lower"),
	originalBasionymAuthorTeam("original basionym author team", "original_basionym_author_team_s_lower"),
	originalCitedType("original cited type", "original_cited_type_s_lower"),
	originalHybridParentage("original hybrid parentage", "original_hybrid_parentage_s_lower"),
	originalParentCitationTaxonNameAuthorTeam("original parent citation taxon name author team", "original_parent_citation_taxon_name_author_team_s_lower"),
	originalReplacedSynonym("original replaced synonym", "original_replaced_synonym_s_lower"),
	originalReplacedSynonymAuthorTeam("original replaced synonym author team", "original_replaced_synonym_author_team_s_lower"),
	originalTaxonDistribution("original taxon distribution", "original_taxon_distribution_s_lower"),
	originalTaxonName("original taxon name", "original_taxon_name_s_lower"),
	originalTaxonNameAuthorTeam("original taxon name author team", "original_taxon_name_author_team_s_lower"),
	otherLinks("other links", "other_links_s_lower"),
	primaryPagination("primary pagination", "primary_pagination_s_lower"),
	publicationAuthor("publication author", "publication_author_s_lower"),
	publicationYear("publication year", "publication_year_s_lower"),
	publicationYearFull("publication year full", "publication_year_full_s_lower"),
	publicationYearNote("publication year note", "publication_year_note_s_lower"),
	publicationYearText("publication year text", "publication_year_text_s_lower"),
	referenceRemarks("reference remarks", "reference_remarks_s_lower"),
	remarks("remarks", "remarks_s_lower"),
	secondaryPagination("secondary pagination", "secondary_pagination_s_lower"),
	speciesAuthor("species author", "species_author_s_lower"),
	standardisedBasionymAuthorFlag("standardised basionym author flag", "standardised_basionym_author_flag_s_lower"),
	standardisedPublicationFlag("standardised publication flag", "standardised_publication_flag_s_lower"),
	standardisedPublishingAuthorFlag("standardised publishing author flag", "standardised_publishing_author_flag_s_lower"),
	startPage("start page", "start_page_s_lower"),
	suppressed("suppressed", "suppressed_b"),
	typeLocations("type locations", "type_locations_s_lower"),
	typeName("type name", "type_name_s_lower"),
	typeRemarks("type remarks", "type_remarks_s_lower"),
	version("version", "version_s_lower"),
	volume("volume", "volume_s_lower"),
	;

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
