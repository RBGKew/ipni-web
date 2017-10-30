SELECT
  CASE citation_type_id
    WHEN '1-1' THEN 'tax. nov'
    WHEN '2-1' THEN 'comb. nov*'
    WHEN '4-1' THEN 'comb. nov*'
    WHEN '6-1' THEN 'comb. nov*'
    WHEN '3-1' THEN 'nom. nov**'
    WHEN '5-1' THEN 'nom. nov**'
  END AS citation_type
  , CASE rank.rank_type_id
    WHEN '1-1' THEN 'infraspecific'
    WHEN '2-1' THEN 'specific'
    WHEN '3-1' THEN 'infrageneric'
    WHEN '4-1' THEN 'generic'
    WHEN '5-1' THEN 'infrafamilial'
    WHEN '6-1' THEN 'familial'
    WHEN '10-1' THEN 'other'
  END AS rank
  , reference.publication_year as year_published
  , count(*) as Total
  FROM reference
  INNER JOIN citation ON reference.citation_id = citation.citation_id
    INNER JOIN taxon_name ON citation.taxon_name_id = taxon_name.taxon_name_id
    INNER JOIN rank ON taxon_name.rank_id = rank.rank_id
  WHERE reference.publication_year > 1752
    AND citation_type_id IN ('1-1', '2-1', '3-1', '4-1', '5-1', '6-1')
  GROUP BY citation_type, rank, year_published;
