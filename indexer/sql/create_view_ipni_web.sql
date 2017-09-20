-- View: ipni_web

DROP VIEW ipni_web;

CREATE OR REPLACE VIEW ipni_web AS
 SELECT 'urn:lsid:ipni.org:names:'||ipni_flat_joined.id AS id,
    replace(ipni_flat_joined.authors, '"', '') AS authors_t,
    replace(ipni_flat_joined.basionym, '"', '') AS basionym_s_lower,
    ipni_flat_joined.basionym_author AS basionym_author_s_lower,
    ipni_flat_joined.basionym_id AS lookup_basionym_id,
    replace(ipni_flat_joined.bibliographic_reference, '"', '') AS bibliographic_reference_s_lower,
    ipni_flat_joined.bibliographic_type_info AS bibliographic_type_info_s_lower,
    replace(ipni_flat_joined.reference_collation, '"', '') AS reference_collation_s_lower,
    replace(ipni_flat_joined.collection_date_as_text, '"', '') AS collection_date_as_text_s_lower,
    ipni_flat_joined.collection_day_1 AS collection_day_1_s_lower,
    ipni_flat_joined.collection_day_2 AS collection_day_2_s_lower,
    ipni_flat_joined.collection_month_1 AS collection_month_1_s_lower,
    ipni_flat_joined.collection_month_2 AS collection_month_2_s_lower,
    replace(ipni_flat_joined.collection_number, '"', '') AS collection_number_s_lower,
    ipni_flat_joined.collection_year_1 AS collection_year_1_s_lower,
    ipni_flat_joined.collection_year_2 AS collection_year_2_s_lower,
    regexp_replace(replace(ipni_flat_joined.collector_team_as_text, '"', ''), '\r|\n', '', 'g') AS collector_team_as_text_t,
    ipni_flat_joined.conserved_against_id AS conserved_against_id_s_lower,
    ipni_flat_joined.correction_of_id AS correction_of_id_s_lower,
    ipni_flat_joined.date_created AS date_created_date,
    ipni_flat_joined.date_last_modified AS date_last_modified_date,
    replace(ipni_flat_joined.distribution, '"', '') AS distribution_s_lower,
    ipni_flat_joined.east_or_west AS east_or_west_s_lower,
    ipni_flat_joined.family AS family_s_lower,
    replace(ipni_flat_joined.full_name_without_family_and_authors, '"', '') AS taxon_scientific_name_s_lower,
        CASE
            WHEN ipni_flat_joined.suppressed THEN ''
            ELSE replace(ipni_flat_joined.full_name_without_family_and_authors, '"', '')
        END AS taxon_sci_name_not_suppressed_s_lower,
    replace(ipni_flat_joined.genus, '"', '') AS genus_s_lower,
    ipni_flat_joined.geographic_unit_as_text AS geographic_unit_as_text_s_lower,
    ipni_flat_joined.hybrid AS hybrid_b,
    ipni_flat_joined.hybrid_genus AS hybrid_genus_b,
    ipni_flat_joined.hybrid_parent_id AS lookup_hybrid_parent_id,
    ipni_flat_joined.hybrid_parents AS hybrid_parents_s_lower,
    ipni_flat_joined.infra_family AS infra_family_s_lower,
    replace(ipni_flat_joined.infra_genus, '"', '') AS infra_genus_s_lower,
    replace(ipni_flat_joined.infraspecies, '"', '') AS infraspecies_s_lower,
    ipni_flat_joined.isonym_of_id AS isonym_of_id_s_lower,
    ipni_flat_joined.later_homonym_of_id AS lookup_later_homonym_of_id,
    ipni_flat_joined.latitude_degrees AS latitude_degrees_s_lower,
    ipni_flat_joined.latitude_minutes AS latitude_minutes_s_lower,
    ipni_flat_joined.latitude_seconds AS latitude_seconds_s_lower,
    replace(ipni_flat_joined.locality, '"', '') AS locality_s_lower,
    ipni_flat_joined.longitude_degrees AS longitude_degrees_s_lower,
    ipni_flat_joined.longitude_minutes AS longitude_minutes_s_lower,
    ipni_flat_joined.longitude_seconds AS longitude_seconds_s_lower,
    regexp_replace(replace(ipni_flat_joined.name_status, '"', ''), '\r|\n', '', 'g') AS name_status_s_lower,
    replace(ipni_flat_joined.nomenclatural_synonym, '"', '') AS nomenclatural_synonym_s_lower,
    ipni_flat_joined.nomenclatural_synonym_id AS lookup_nomenclatural_synonym_id,
    ipni_flat_joined.north_or_south AS north_or_south_s_lower,
    replace(ipni_flat_joined.original_basionym, '"', '') AS original_basionym_s_lower,
    ipni_flat_joined.original_basionym_author_team AS original_basionym_author_team_s_lower,
    ipni_flat_joined.original_hybrid_parentage AS original_hybrid_parentage_s_lower,
    replace(ipni_flat_joined.original_remarks, '"', '') AS original_remarks_s_lower,
    replace(ipni_flat_joined.original_replaced_synonym, '"', '') AS original_replaced_synonym_s_lower,
    ipni_flat_joined.original_taxon_distribution AS original_taxon_distribution_s_lower,
    ipni_flat_joined.orthographic_variant_of_id AS lookup_orthographic_variant_of_id,
    ipni_flat_joined.other_links AS other_links_s_lower,
    ipni_flat_joined.parent_id AS lookup_parent_id,
    replace(ipni_flat_joined.publication, '"', '') AS publication_s_lower,
    CASE
	WHEN ipni_flat_joined.publication_id IS NOT NULL THEN 'urn:lsid:ipni.org:publications:'||ipni_flat_joined.publication_id
    END AS publication_id,
    ipni_flat_joined.publication_year AS publication_year_i,
    replace(ipni_flat_joined.publication_year_full, '"', '') AS publication_year_full_s_lower,
    replace(ipni_flat_joined.publication_year_note, '"', '') AS publication_year_note_s_lower,
    replace(ipni_flat_joined.publishing_author, '"', '') AS publishing_author_s_lower,
    ipni_flat_joined.rank AS rank_s_alphanum,
    replace(ipni_flat_joined.reference, '"', '') AS reference_t,
    replace(ipni_flat_joined.reference_remarks, '"', '') AS reference_remarks_s_lower,
    replace(ipni_flat_joined.remarks, '"', '') AS remarks_s_lower,
    ipni_flat_joined.replaced_synonym_id AS replaced_synonym_id_s_lower,
    ipni_flat_joined.same_citation_as_id AS lookup_same_citation_as_id,
    ipni_flat_joined.score AS score_s_lower,
    replace(ipni_flat_joined.species, '"', '') AS species_s_lower,
    ipni_flat_joined.species_author AS species_author_s_lower,
    ipni_flat_joined.superfluous_name_of_id AS lookup_superfluous_name_of_id,
    ipni_flat_joined.suppressed AS suppressed_b,
    ipni_flat_joined.top_copy AS top_copy_b,
    ipni_flat_joined.type_id AS type_id_s_lower,
    replace(ipni_flat_joined.type_locations, '"', '') AS type_locations_s_lower,
    replace(ipni_flat_joined.type_name, '"', '') AS type_name_s_lower,
    replace(ipni_flat_joined.type_remarks, '"', '') AS type_remarks_s_lower,
    replace(ipni_flat_missing_fields.chosen_by, '"', '') AS type_chosen_by_s_lower,
    replace(ipni_flat_missing_fields.type_specimen_note, '"', '') AS type_note_s_lower,
    replace(ipni_flat_missing_fields.author_team_ids, '"', '') AS detail_author_team_ids,
    replace(ipni_flat_missing_fields.species_author_team_ids, '"', '') AS detail_species_author_team_ids,
    ipni_flat_joined.validation_of_id AS lookup_validation_of_id,
    ipni_flat_joined.version AS version_s_lower,
    CASE
        WHEN ipni_ids_in_powo.id IS NOT NULL THEN true
        ELSE false
    END AS powo_b    
   FROM ipni_flat_joined
   LEFT JOIN ipni_flat_missing_fields ON ipni_flat_joined.id = ipni_flat_missing_fields.id
   LEFT JOIN ipni_ids_in_powo ON ipni_flat_joined.id = ipni_ids_in_powo.id
  ORDER BY ipni_flat_joined.id;

ALTER TABLE ipni_web
  OWNER TO ipni_flat_writer;
GRANT ALL ON TABLE ipni_web TO ipni_flat_writer;
GRANT SELECT ON TABLE ipni_web TO ipni_flat;
