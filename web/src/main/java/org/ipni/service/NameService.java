package org.ipni.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.ipni.model.Name;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

@Service
public class NameService {
	
	private Logger logger = LoggerFactory.getLogger(NameService.class);
	@Autowired
	SolrClient solr;
	
	public Name load(String id) throws SolrServerException, IOException {
		ModifiableSolrParams params = new ModifiableSolrParams().add("fl", "*");
		SolrDocument result = solr.getById(id, params);
		Name name = new Name(result);
		logger.info("full result: " + result);
		Map<String, List<String>> ids = getIds(result);
		Map<String, List<Name>> names = getNames(ids);
		logger.info("names:" + names);
		name.setBasionym(names.get("lookup_basionym_id"));
		name.setConservedAgainst(names.get("lookup_conserved_against_id"));
		name.setCorrectionOf(names.get("lookup_correction_of_id"));
		name.setHybridParents(names.get("lookup_hybrid_parent_id"));
		name.setIsonymOf(names.get("lookup_isonym_of_id"));
		name.setLaterHomonymOf(names.get("lookup_later_homonym_of_id"));
		name.setNomenclaturalSynonym(names.get("lookup_nomenclatural_synonym_id"));
		name.setReplacedSynonym(names.get("lookup_replaced_synonym_id"));
		name.setSameCitationAs(names.get("lookup_same_citation_as_id"));
		name.setSuperfluousNameOf(names.get("lookup_superfluous_name_of_id"));
		name.setValidationOf(names.get("lookup_validation_of_id"));
		name.setParent(names.get("lookup_parent_id"));
		name.setOrthographicVariantOf(names.get("lookup_orthographic_variant_of_id"));
		return name;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, List<String>> getIds(SolrDocument result) throws SolrServerException, IOException{
		Map<String, List<String>> idFieldMapping = new HashMap<String, List<String>>();
		Collection<String> fieldNames = result.getFieldNames();
		fieldNames.forEach(item -> {
			if(item.endsWith("_id")){
				Collection<String> ids = (Collection<String>)(Collection<?>) result.getFieldValues(item);
				ids.forEach(id -> {
					if(idFieldMapping.containsKey(id)){
						List<String> fieldList = idFieldMapping.get(id);
						fieldList.add(item);
						idFieldMapping.put(id, fieldList);
					}else{
						List<String> fieldList = new ArrayList<String>();
						fieldList.add(item);
						idFieldMapping.put(id, fieldList);
					}
				});
			}
		});
		return idFieldMapping;
	}
	
	private Map<String, List<Name>> getNames(Map<String, List<String>> ids) throws SolrServerException, IOException{
		List<SolrDocument> docs = solr.getById(ids.keySet());
		Map<String, List<Name>> names = new HashMap<String, List<Name>>();
		for(SolrDocument doc : docs){
			List<String> fields = ids.get((String) doc.getFirstValue("id"));
			Name name = new Name(doc);
			for(String field : fields){
				if(names.containsKey(field)){
					List<Name> nameList = names.get(field);
					nameList.add(name);
					names.put(field, nameList);
				}else{
					List<Name> nameList = new ArrayList<Name>();
					nameList.add(name);
					names.put(field, nameList);
				}
			}
		}
		return names;
	}


}
