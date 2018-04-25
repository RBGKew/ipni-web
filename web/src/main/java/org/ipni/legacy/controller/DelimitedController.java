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
import org.ipni.legacy.model.DelimitedClassic;
import org.ipni.legacy.model.DelimitedExtended;
import org.ipni.legacy.model.DelimitedField;
import org.ipni.legacy.model.DelimitedMinimal;
import org.ipni.legacy.model.DelimitedShort;
import org.ipni.search.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DelimitedController {

	@Autowired
	SolrClient solr;

	@ResponseBody
	@RequestMapping(value = "/ipni/search/delimited", method = RequestMethod.GET, produces = {"text/plain"})
	public String search(@RequestParam Map<String, String> params) throws SolrServerException, IOException {
		params.put("perPage", "5000");

		SolrQuery query = new QueryBuilder(LegacyParamMapper.translate(params)).build();
		SolrDocumentList results = solr.query(query).getResults();
		String format = params.remove("output_format");

		StringBuilder sb = new StringBuilder();
		sb.append(getHeaders(format));
		sb.append("\n");
		results.forEach(res -> sb.append(getDelimited(format, res)));
		return sb.toString();
	}

	private String getHeaders(String format) {
		List<DelimitedField> fields;
		switch (format) {
		case "delimited-minimal":
			fields = DelimitedMinimal.fields;
			break;
		case "delimited-short":
			fields = DelimitedShort.fields;
			break;
		case "delimited-extended":
			fields = DelimitedExtended.fields;
			break;
		default:
			fields = DelimitedClassic.fields;
		}

		return fields.stream().map(DelimitedField::display).collect(joining("%"));
	}

	private String getDelimited(String format, SolrDocument doc) {
		switch (format) {
		case "delimited-minimal":
			return new DelimitedMinimal(doc).toDelimited();
		case "delimited-short":
			return new DelimitedShort(doc).toDelimited();
		case "delimited-extended":
			return new DelimitedExtended(doc).toDelimited();
		default:
			return new DelimitedClassic(doc).toDelimited();
		}
	}
}
