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
import org.ipni.legacy.model.DelimitedPlantNameClassic;
import org.ipni.legacy.model.DelimitedPlantNameExtended;
import org.ipni.legacy.model.DelimitedPlantNameField;
import org.ipni.legacy.model.DelimitedPlantNameMinimal;
import org.ipni.legacy.model.DelimitedPlantNameShort;
import org.ipni.search.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DelimitedPlantNameController {

	@Autowired
	SolrClient solr;

	@ResponseBody
	@RequestMapping(value = "/ipni/search/plantname/delimited", method = RequestMethod.GET, produces = {"text/plain"})
	public String search(@RequestParam Map<String, String> params) throws SolrServerException, IOException {
		params.put("perPage", "5000");

		SolrQuery query = new QueryBuilder(LegacyPlantNameParamMapper.translate(params)).build();
		SolrDocumentList results = solr.query(query).getResults();
		String format = params.remove("output_format");

		StringBuilder sb = new StringBuilder();
		sb.append(getHeaders(format));
		sb.append("\n");
		results.forEach(res -> sb.append(getDelimited(format, res)));
		return sb.toString();
	}

	private String getHeaders(String format) {
		List<DelimitedPlantNameField> fields;
		switch (format) {
		case "delimited-minimal":
			fields = DelimitedPlantNameMinimal.fields;
			break;
		case "delimited-short":
			fields = DelimitedPlantNameShort.fields;
			break;
		case "delimited-extended":
			fields = DelimitedPlantNameExtended.fields;
			break;
		default:
			fields = DelimitedPlantNameClassic.fields;
		}

		return fields.stream().map(DelimitedPlantNameField::getDisplay).collect(joining("%"));
	}

	private String getDelimited(String format, SolrDocument doc) {
		switch (format) {
		case "delimited-minimal":
			return new DelimitedPlantNameMinimal(doc).toDelimited();
		case "delimited-short":
			return new DelimitedPlantNameShort(doc).toDelimited();
		case "delimited-extended":
			return new DelimitedPlantNameExtended(doc).toDelimited();
		default:
			return new DelimitedPlantNameClassic(doc).toDelimited();
		}
	}
}
