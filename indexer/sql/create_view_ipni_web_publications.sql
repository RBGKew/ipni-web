DROP VIEW ipni_web_publications;

CREATE OR REPLACE VIEW ipni_web_publications AS 
 SELECT 'urn:lsid:ipni.org:publications:'||id AS id,
        version AS version_s_lower,
        'p' AS ipni_record_type_s_lower,
        true AS top_copy_b,
        suppressed AS suppressed_b,
        REPLACE(abbreviation, '"','') AS abbreviation_s_lower,
        REPLACE(REPLACE(title,'"',''), '|','') AS title_s_lower,
        REPLACE(REPLACE(remarks, '"',''),'|','') AS remarks_s_lower,
        REPLACE(bph_number, '"','') AS bph_number_s_lower,
        REPLACE(isbn, '"','') AS isbn_s_lower,
        REPLACE(issn,'|','') AS issn_s_lower,
        REPLACE(date, '"','') AS date_s_lower,
        REPLACE(lc_number, '"','') AS lc_number_s_lower,
        REPLACE(preceded_by, '"','') AS preceded_by_s_lower,
        REPLACE(tl2_author,'|','') AS tl2_author_s_lower,
        REPLACE(REPLACE(tl2_number, '"',''),'|','') AS tl2_number_s_lower,
        REPLACE(tdwg_abbreviation, '"','') AS tdwg_abbreviation_s_lower,
        REPLACE(superceded_by, '"','') AS superceded_by_s_lower,
        REPLACE(REPLACE(lower(title),'"',''), '|','') AS sortable
   FROM ipni_flat_publications; 

ALTER TABLE ipni_web_publications
  OWNER TO ipni_flat_writer;
GRANT ALL ON TABLE ipni_web_publications TO ipni_flat_writer;
GRANT SELECT ON TABLE ipni_web_publications TO ipni_flat;
