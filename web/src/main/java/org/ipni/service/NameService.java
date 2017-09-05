package org.ipni.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.ipni.model.Name;
import org.ipni.model.NameAuthor;
import org.ipni.util.IdUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

@Service
public class NameService {

	private Logger logger = LoggerFactory.getLogger(NameService.class);

	@Autowired
	private SolrClient solr;

	public Name load(String id) throws SolrServerException, IOException {

		if(!id.startsWith("urn:lsid:ipni.org:names")) {
			id = "urn:lsid:ipni.org:names:" + id;
		}

		ModifiableSolrParams params = new ModifiableSolrParams().add("fl", "*");
		SolrDocument result = solr.getById(id, params);
		Name name = new Name(result);
		Map<String, Name> relatedNames = getRelatedNames(result);

		name.setBasionym(lookup(result.get("lookup_basionym_id"), relatedNames));
		name.setConservedAgainst(lookup(result.get("lookup_conserved_against_id"), relatedNames));
		name.setCorrectionOf(lookup(result.get("lookup_correction_of_id"), relatedNames));
		name.setHybridParents(lookup(result.get("lookup_hybrid_parent_id"), relatedNames));
		name.setIsonymOf(lookup(result.get("lookup_isonym_of_id"), relatedNames));
		name.setLaterHomonymOf(lookup(result.get("lookup_later_homonym_of_id"), relatedNames));
		name.setNomenclaturalSynonym(lookup(result.get("lookup_nomenclatural_synonym_id"), relatedNames));
		name.setReplacedSynonym(lookup(result.get("lookup_replaced_synonym_id"), relatedNames));
		name.setSameCitationAs(lookup(result.get("lookup_same_citation_as_id"), relatedNames));
		name.setSuperfluousNameOf(lookup(result.get("lookup_superfluous_name_of_id"), relatedNames));
		name.setValidationOf(lookup(result.get("lookup_validation_of_id"), relatedNames));
		name.setParent(lookup(result.get("lookup_parent_id"), relatedNames));
		name.setOrthographicVariantOf(lookup(result.get("lookup_orthographic_variant_of_id"), relatedNames));
		name.setAuthorTeam(parseAuthorTeam(result));

		return name;
	}

	@SuppressWarnings("unchecked")
	private List<Name> lookup(Object lookup, Map<String, Name> relatedNames) {
		if(lookup == null) {
			return null;
		}

		return ((List<String>) lookup).stream()
				.map(id -> relatedNames.get(id))
				.filter(Objects::nonNull)
				.collect(Collectors.<Name>toList());
	}

	@SuppressWarnings("unchecked")
	private List<NameAuthor> parseAuthorTeam(SolrDocument result) {
		if(result.get("detail_author_team_ids") == null) {
			return null;
		}

		return ((List<String>) result.get("detail_author_team_ids")).stream()
				.map(str -> new NameAuthor(str))
				.collect(Collectors.<NameAuthor>toList());
	}

	private Map<String, Name> getRelatedNames(SolrDocument result) throws SolrServerException, IOException {
		if(!result.containsKey("lookup_all")) {
			return new HashMap<>();
		}

		List<String> relatedIds = result.getFieldValues("lookup_all").stream()
				.map(id -> IdUtil.fqName(id.toString()))
				.collect(Collectors.<String>toList());

		return solr.getById(relatedIds).stream()
				.map(doc -> new Name(doc))
				.collect(Collectors.toMap(Name::getId, name -> name));
	}

}