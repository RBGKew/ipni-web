package org.ipni.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.ipni.constants.FieldMapping;
import org.ipni.model.Name;
import org.ipni.model.NameAuthor;
import org.ipni.util.IdUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Functions;
import org.slf4j.Logger;

@Service
public class NameService {

	private Logger logger = LoggerFactory.getLogger(NameService.class);

	@Autowired
	private SolrClient solr;

	@Autowired
	private PublicationService publications;

	public Name load(String id, FieldMapping... fields) throws SolrServerException, IOException {
		if(id == null) {
			return null;
		}

		id = IdUtil.fqName(id);

		String fieldList = Arrays.asList(fields).stream()
				.map(FieldMapping::solrField)
				.collect(Collectors.joining(","));
		ModifiableSolrParams params = new ModifiableSolrParams().add("fl", fieldList);
		SolrDocument result = solr.getById(id, params);
		Name name = new Name(result);
		Map<String, Name> relatedNames = getRelatedNames(result);

		name.setBasionym(lookup(result.getFieldValues("lookup_basionym_id"), relatedNames));
		name.setConservedAgainst(lookup(result.getFieldValues("lookup_conserved_against_id"), relatedNames));
		name.setCorrectionOf(lookup(result.getFieldValues("lookup_correction_of_id"), relatedNames));
		name.setHybridParents(lookup(result.getFieldValues("lookup_hybrid_parent_id"), relatedNames));
		name.setIsonymOf(lookup(result.getFieldValues("lookup_isonym_of_id"), relatedNames));
		name.setLaterHomonymOf(lookup(result.getFieldValues("lookup_later_homonym_of_id"), relatedNames));
		name.setNomenclaturalSynonym(lookup(result.getFieldValues("lookup_nomenclatural_synonym_id"), relatedNames));
		name.setReplacedSynonym(lookup(result.getFieldValues("lookup_replaced_synonym_id"), relatedNames));
		name.setSameCitationAs(lookup(result.getFieldValues("lookup_same_citation_as_id"), relatedNames));
		name.setSuperfluousNameOf(lookup(result.getFieldValues("lookup_superfluous_name_of_id"), relatedNames));
		name.setValidationOf(lookup(result.getFieldValues("lookup_validation_of_id"), relatedNames));
		name.setParent(lookup(result.getFieldValues("lookup_parent_id"), relatedNames));
		name.setOrthographicVariantOf(lookup(result.getFieldValues("lookup_orthographic_variant_of_id"), relatedNames));
		name.setAuthorTeam(parseAuthorTeam(result));
		name.setLinkedPublication(publications.load((String)result.getFirstValue("lookup_publication_id")));

		return name;
	}

	public Name load(String id) throws SolrServerException, IOException {
		return load(id, FieldMapping.all);
	}

	private List<Name> lookup(Collection<Object> lookup, Map<String, Name> relatedNames) {
		if(lookup == null) {
			return null;
		}

		return lookup.stream()
				.map(id -> relatedNames.get(IdUtil.fqName(id.toString())))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	private List<NameAuthor> parseAuthorTeam(SolrDocument result) {
		if(result.get("detail_author_team_ids") == null) {
			return null;
		}

		return ((List<String>) result.get("detail_author_team_ids")).stream()
				.map(NameAuthor::new)
				.collect(Collectors.toList());
	}

	private List<String> getRelatedIds(SolrDocument result) {
		if(!result.getFieldNames().stream().anyMatch(name -> name.startsWith("lookup_"))) {
			logger.debug("No lookup_* fields found");
			return new ArrayList<>();
		}

		return result.getFieldNames().stream()
				.filter(name -> name.startsWith("lookup_"))
				.map(result::getFieldValues)
				.flatMap(Collection::stream)
				.map(id -> IdUtil.fqName(id.toString()))
				.collect(Collectors.toList());
	}

	private Map<String, Name> getRelatedNames(SolrDocument result) throws SolrServerException, IOException {
		List<String> relatedIds = getRelatedIds(result);
		if(relatedIds.isEmpty()) {
			return new HashMap<>();
		}

		return solr.getById(relatedIds).stream()
				.filter(doc -> IdUtil.isNameId(doc.getFieldValue("id").toString()))
				.map(Name::new)
				.collect(Collectors.toMap(Name::getFqId, Functions.identity()));
	}

}