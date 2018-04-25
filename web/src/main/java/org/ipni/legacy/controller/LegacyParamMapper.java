package org.ipni.legacy.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.ipni.constants.FieldMapping;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

public class LegacyParamMapper {
	private static final Map<String, String> paramsMap = ImmutableMap.<String, String>builder()
			.put("find_family", FieldMapping.family.apiField())
			.put("find_infrafamily", FieldMapping.infrafamily.apiField())
			.put("find_genus", FieldMapping.genus.apiField())
			.put("find_infragenus", FieldMapping.infragenus.apiField())
			.put("find_species", FieldMapping.species.apiField())
			.put("find_infraspecies", FieldMapping.infraspecies.apiField())
			.put("find_authorAbbrev", FieldMapping.authorStandardForm.apiField())
			.put("find_publicationTitle", FieldMapping.publication.apiField())
			.put("find_modifiedSince", "modified after")
			.put("find_addedSince", "added after")
			.build();

	private static final Map<String, String> filterMap = ImmutableMap.<String, String>builder()
			.put("fam", "f_familial")
			.put("infrafam", "f_infrafamilial")
			.put("gen", "f_generic")
			.put("infragen", "f_infrageneric")
			.put("spec", "f_specific")
			.put("infraspec", "f_infraspecific")
			.build();

	public static Map<String, String> translate(Map<String, String> params) {
		Map<String, String> translated = new HashMap<>();
		String qParams = params.entrySet().stream()
				.map(entry -> formatted(entry))
				.filter(Objects::nonNull)
				.collect(Collectors.joining(","));
		translated.put("q", qParams);

		String rank = params.get("find_rankToReturn");
		if(!(Strings.isNullOrEmpty(rank) || "all".equals(rank))) {
			translated.put("f", filterMap.getOrDefault(rank, rank));
		}

		return translated;
	}

	private static String formatted(Map.Entry<String, String> entry) {
		if (Strings.isNullOrEmpty(entry.getValue()) || !paramsMap.containsKey(entry.getKey())) {
			return null;
		} else {
			return String.format("%s:%s", paramsMap.get(entry.getKey()), entry.getValue());
		}
	}
}