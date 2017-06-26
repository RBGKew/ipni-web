package org.ipni.constants;

public enum FieldMapping {

	scientificName("scientific name", "taxon_scientific_name_s_lower"),
	family("family", "family_s_lower"),
	genus("genus", "genus_s_lower"),
	species("species", "species_s_lower"),
	infraspecies("infraspecies", "infraspecies_s_lower"),
	rank("rank", "taxon_rank_s_lower"),
	hybrid("hybrid", "hybrid_s_lower"),
	hybridGenus("hybrid genus", "hybrid_genus_s_lower"),
	author("author", "authors_s_lower"),
	reference("reference", "reference_s_lower");

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
