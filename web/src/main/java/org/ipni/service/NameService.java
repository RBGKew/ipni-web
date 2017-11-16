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
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.ipni.constants.FieldMapping;
import org.ipni.constants.LinkType;
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

		String fieldList = Arrays.asList(fields).stream().map(FieldMapping::solrField).collect(Collectors.joining(","));
		ModifiableSolrParams params = new ModifiableSolrParams().add("fl", fieldList);
		SolrDocument result = solr.getById(id, params);
		Name name = new Name(result);
		Map<String, Name> relatedNames = getRelatedNames(result);
		Map<String, List<Name>> inboundLinks = getInboundNames(result);

		for(LinkType link : LinkType.values()) {
			link.getForwardSetter()
			.andThen((n, l) -> logger.debug("Setting: {} = {}", link.getForwardField(), l))
			.accept(name, lookup(result.getFieldValues(link.getForwardField()), relatedNames));

			if(inboundLinks.containsKey(link.getReverseField())) {
				link.getReverseSetter()
				.andThen((n, l) -> logger.debug("Setting: {} = {}", link.getReverseField(), l))
				.accept(name, inboundLinks.get(link.getReverseField()));
			}
		}

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

	private List<String> getOutboundIds(SolrDocument result) {
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

	private Map<String, List<Name>> getInboundNames(SolrDocument result) throws SolrServerException, IOException {
		SolrQuery q = new SolrQuery();
		String inboundId = IdUtil.idPart((String)result.getFieldValue("id"));
		q.setQuery("lookup_all:" + inboundId);
		SolrDocumentList results = solr.query(q).getResults();
		Map<String, List<Name>> mapping = new HashMap<>();

		for(SolrDocument res : results) {
			Name nameResult = new Name(res);
			for(LinkType link : LinkType.values()) {
				Collection<Object> ids = res.getFieldValues(link.getForwardField());
				if(ids != null && ids.stream().anyMatch(id -> id.toString().equals(inboundId))) {
					logger.debug("found {} in {}", inboundId, ids);

					if(!mapping.containsKey(link.getReverseField())) {
						mapping.put(link.getReverseField(), new ArrayList<>());
					}

					mapping.get(link.getReverseField()).add(nameResult);
				}
			}
		}

		logger.debug("Inbound names: {}", mapping);

		return mapping;
	}

	private Map<String, Name> getRelatedNames(SolrDocument result) throws SolrServerException, IOException {
		List<String> outboundIds = getOutboundIds(result);
		if(outboundIds.isEmpty()) {
			return new HashMap<>();
		}

		return solr.getById(outboundIds).stream()
				.filter(doc -> IdUtil.isNameId(doc.getFieldValue("id").toString()))
				.map(Name::new)
				.collect(Collectors.toMap(Name::getFqId, Functions.identity(), (a,b) -> a));
	}

}