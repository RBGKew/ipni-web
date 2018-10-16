DROP VIEW ipni_web_authors;

CREATE OR REPLACE VIEW ipni_web_authors AS 
SELECT 'urn:lsid:ipni.org:authors:'||id AS id,
       version AS version_s_lower,
       'a' AS ipni_record_type_s_lower,
       true AS top_copy_b,
       suppressed AS suppressed_b,
       default_author_name AS author_name_s_lower,
       default_author_forename AS author_forename_s_lower,
       default_author_surname AS author_surname_s_lower,
       standard_form AS standard_form_s_lower,
       REPLACE(name_notes,'"','') AS name_notes_s_lower,
       name_source AS name_source_s_lower,
       dates AS dates_s_lower,
       date_type_string AS date_type_string_s_lower,
       alternative_abbreviations AS alternative_abbreviations_s_lower,
       alternative_names AS detail_alternative_names,
       example_of_name_published AS example_of_name_published_s_lower,
       REPLACE(comments,'"','') AS comments_s_lower,
       author_iso_countries AS detail_author_iso_countries,
       taxon_groups_flat AS detail_taxon_groups_flat,
       COALESCE(lower(default_author_name),null,'') AS sortable
 FROM  ipni_flat_authors;

ALTER TABLE ipni_web_authors
  OWNER TO ipni_flat_writer;
GRANT ALL ON TABLE ipni_web_authors TO ipni_flat_writer;
GRANT SELECT ON TABLE ipni_web_authors TO ipni_flat;