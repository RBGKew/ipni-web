package org.ipni.legacy.controller;

import static java.util.stream.Collectors.joining;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.ipni.legacy.model.DelimitedAuthorClassic;
import org.ipni.legacy.model.DelimitedAuthorExtended;
import org.ipni.legacy.model.DelimitedAuthorField;
import org.ipni.legacy.model.DelimitedAuthorMinimal;
import org.ipni.search.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DelimitedAuthorController {

	@Autowired
	SolrClient solr;

	@ResponseBody
	@RequestMapping(value = "/ipni/search/author/delimited", method = RequestMethod.GET, produces = {"text/plain"})
	public String search(@RequestParam Map<String, String> params) throws SolrServerException, IOException {
		params.put("perPage", "5000");

		SolrQuery query = new QueryBuilder(LegacyAuthorParamMapper.translate(params)).build();
		SolrDocumentList results = solr.query(query).getResults();
		String format = params.remove("output_format");

		StringBuilder sb = new StringBuilder();
		sb.append(getHeaders(format));
		sb.append("\n");
		results.forEach(res -> sb.append(getDelimited(format, res)));
		return sb.toString();
	}

	private String getHeaders(String format) {
		List<DelimitedAuthorField> fields;
		switch (format) {
		case "delimited-minimal":
		case "delimited-short":
			fields = DelimitedAuthorMinimal.fields;
			break;
		case "delimited-extended":
			fields = DelimitedAuthorExtended.fields;
			break;
		default:
			fields = DelimitedAuthorClassic.fields;
		}

		return fields.stream().map(DelimitedAuthorField::getDisplay).collect(joining("%"));
	}

	private String getDelimited(String format, SolrDocument doc) {
		switch (format) {
		case "delimited-minimal":
		case "delimited-short":
			return new DelimitedAuthorMinimal(doc).toDelimited();
		case "delimited-extended":
			return new DelimitedAuthorExtended(doc).toDelimited();
		default:
			return new DelimitedAuthorClassic(doc).toDelimited();
		}
	}
}