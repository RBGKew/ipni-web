SELECT CASE SUBSTRING_INDEX(contribution.object_class, '.', -1)
	WHEN 'Citation' THEN 'Name citations'
	WHEN 'Author' THEN 'Authors'
	WHEN 'Publication' THEN 'Publications'
	END AS object
  , CASE contribution.version
    WHEN '1.1' THEN 'Addition'
    ELSE 'Update'
  END AS type
  , SUBSTRING(contribution_date,6,2) AS month
  , LEFT(contribution_date,4) as year
	, count(*) as total
	FROM contribution
	WHERE contribution.object_class NOT IN ('org.ipni.plantname.security.bo.User', 'org.ipni.plantname.citation.bo.Rank')
	 AND contribution.user_id <> '1-1'
	GROUP BY object, type, year, month;
