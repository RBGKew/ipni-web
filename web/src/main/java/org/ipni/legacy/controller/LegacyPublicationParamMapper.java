package org.ipni.legacy.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.ipni.constants.FieldMapping;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

public class LegacyPublicationParamMapper {
	private static final Map<String, String> paramsMap = ImmutableMap.<String, String>builder()
			.put("find_abbreviation", FieldMapping.abbreviation.apiField())
			.put("find_title", FieldMapping.title.apiField()).build();

	public static Map<String, String> translate(Map<String, String> params) {
		Map<String, String> translated = new HashMap<>();
		String qParams = params.entrySet().stream()
				.map(entry -> formatted(entry))
				.filter(Objects::nonNull)
				.collect(Collectors.joining(","));
		translated.put("q", qParams);
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
