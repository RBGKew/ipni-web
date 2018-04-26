package org.ipni.legacy.controller;

import static java.util.stream.Collectors.joining;

import java.io.IOException;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.ipni.legacy.model.DelimitedPublication;
import org.ipni.legacy.model.DelimitedPublicationField;
import org.ipni.search.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DelimitedPublicationController {

	@Autowired
	SolrClient solr;

	@ResponseBody
	@RequestMapping(value = "/ipni/search/publication/delimited", method = RequestMethod.GET, produces = {"text/plain"})
	public String search(@RequestParam Map<String, String> params) throws SolrServerException, IOException {
		params.put("perPage", "5000");

		SolrQuery query = new QueryBuilder(LegacyPublicationParamMapper.translate(params)).build();
		SolrDocumentList results = solr.query(query).getResults();

		StringBuilder sb = new StringBuilder();
		String header = DelimitedPublication.fields.stream()
				.map(DelimitedPublicationField::getDisplay)
				.collect(joining("%", "", "\n"));
		sb.append(header);
		results.forEach(res -> sb.append(new DelimitedPublication(res).toDelimited()));
		return sb.toString();
	}
}
