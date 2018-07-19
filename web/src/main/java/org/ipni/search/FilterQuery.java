package org.ipni.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.ipni.constants.FieldMapping;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

public class FilterQuery extends QueryOption {

	// All ranks have had all non-alphanumerics stripped to allow for sensible query construction
	// This means that the rank field in the solr index must also be transformed to match
	private static final List<String> infrafamilial = ImmutableList.<String>of(
			"infrafamunranked",
			"genser",
			"linea",
			"nothosubtrib",
			"subfam",
			"subtrib",
			"supersubtrib",
			"supertrib",
			"trib");

	private static final List<String> infrageneric = ImmutableList.<String>of(
			"Gruppe",
			"II",
			"infragen",
			"infragengrex",
			"infragenunranked",
			"infragen",
			"agglom",
			"aggr",
			"cinfragen",
			"cycl",
			"genitor",
			"grexsect",
			"group",
			"infragengrex",
			"nothogrex",
			"nothosect",
			"nothoser",
			"nothosubgen",
			"nothosubsect",
			"sect",
			"ser",
			"subgen",
			"subgenitor",
			"subsect",
			"subser",
			"subtribus",
			"supersect",
			"superser",
			"suprasect",
			"suprasp");

	private static final List<String> infraspecific = ImmutableList.<String>of(
			"A",
			"B",
			"C",
			"D",
			"E",
			"G",
			"H",
			"alpha",
			"beta",
			"epsilon",
			"gamma",
			"infraspunranked",
			"agamosp",
			"agamovar",
			"ap",
			"convar",
			"f",
			"fjuv",
			"fspec",
			"forma",
			"grex",
			"group",
			"lus",
			"microgen",
			"microsp",
			"modif",
			"monstr",
			"mut",
			"nm",
			"nothof",
			"notholusus",
			"nothosubsp",
			"nothovar",
			"oec",
			"prol",
			"proles",
			"psp",
			"race",
			"stirps",
			"subf",
			"subhybr",
			"sublus",
			"subsp",
			"subspec",
			"subspecioid",
			"subsubforma",
			"subsubvar",
			"subvar",
			"var");

	@Override
	public void addQueryOption(String key, String value, SolrQuery query) {
		String[] filters = value.split(",");
		List<String> selected = new ArrayList<String>();
		boolean isHybrid = false;
		for(String filter : filters) {
			switch(filter) {
			case "f_familial":
				selected.add("fam.");
				break;
			case "f_infrafamilial":
				selected.addAll(infrafamilial);
				break;
			case "f_generic":
				selected.add("gen.");
				break;
			case "f_infrageneric":
				selected.addAll(infrageneric);
				break;
			case "f_specific":
				selected.add("spec.");
				selected.add("nothospec.");
				break;
			case "f_infraspecific":
				selected.addAll(infraspecific);
				break;
			case "f_hybrid":
				isHybrid = true;
				break;
			}
		}

		if(isHybrid) {
			query.addFilterQuery(FieldMapping.hybrid.solrField() + ":true OR "
					+ FieldMapping.hybridGenus.solrField() + ":true");
		}

		if(!selected.isEmpty()) {
			String filter = String.format("%s: (%s)",
					FieldMapping.rank.solrField(),
					Joiner.on(" ").join(selected));

			query.addFilterQuery(filter);
		}
	}
}
