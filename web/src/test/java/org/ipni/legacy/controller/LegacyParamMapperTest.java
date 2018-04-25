package org.ipni.legacy.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.ipni.constants.FieldMapping.family;
import static org.ipni.constants.FieldMapping.genus;
import static org.ipni.constants.FieldMapping.infrafamily;
import static org.ipni.constants.FieldMapping.infragenus;
import static org.ipni.constants.FieldMapping.infraspecies;
import static org.ipni.constants.FieldMapping.species;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class LegacyParamMapperTest {

	@Test
	public void testBasicParamMapping() {
		Map<String, String> params = ImmutableMap.<String, String>builder()
				.put("find_addedSince", "2018-04-21")
				.put("find_authorAbbrev", "some author")
				.put("find_family", "some family")
				.put("find_genus", "some genus")
				.put("find_infrafamily", "some infrafamily")
				.put("find_infragenus", "some infragenus")
				.put("find_infraspecies", "some infraspecies")
				.put("find_modifiedSince", "2018-04-22")
				.put("find_publicationTitle", "some publication")
				.put("find_species", "some species")
				.put("output_format", "delimited-short")
				.build();

		Map<String, String> translated = LegacyParamMapper.translate(params);

		assertThat(translated.get("q"), containsString(family.apiField() + ":some family"));
		assertThat(translated.get("q"), containsString(infrafamily.apiField() + ":some infrafamily"));
		assertThat(translated.get("q"), containsString(genus.apiField() + ":some genus"));
		assertThat(translated.get("q"), containsString(infragenus.apiField() + ":some infragenus"));
		assertThat(translated.get("q"), containsString(species.apiField() + ":some species"));
		assertThat(translated.get("q"), containsString(infraspecies.apiField() + ":some infraspecies"));
		assertThat(translated.get("q"), containsString("added after:2018-04-21"));
		assertThat(translated.get("q"), containsString("modified after:2018-04-22"));
	}

	@Test
	public void testFamilyRankMapping() {
		Map<String, String> translated = LegacyParamMapper.translate(
				ImmutableMap.<String, String>of("find_rankToReturn", "fam"));
		assertEquals("f_familial", translated.get("f"));
	}

	@Test
	public void testInfraFamilyRankMapping() {
		Map<String, String> translated = LegacyParamMapper.translate(
				ImmutableMap.<String, String>of("find_rankToReturn", "infrafam"));
		assertEquals("f_infrafamilial", translated.get("f"));
	}

	@Test
	public void testGenusRankMapping() {
		Map<String, String> translated = LegacyParamMapper.translate(
				ImmutableMap.<String, String>of("find_rankToReturn", "gen"));
		assertEquals("f_generic", translated.get("f"));
	}

	@Test
	public void testInfraGenusRankMapping() {
		Map<String, String> translated = LegacyParamMapper.translate(
				ImmutableMap.<String, String>of("find_rankToReturn", "infragen"));
		assertEquals("f_infrageneric", translated.get("f"));
	}

	@Test
	public void testSpecificRankMapping() {
		Map<String, String> translated = LegacyParamMapper.translate(
				ImmutableMap.<String, String>of("find_rankToReturn", "spec"));
		assertEquals("f_specific", translated.get("f"));
	}

	@Test
	public void testInfraspecificRankMapping() {
		Map<String, String> translated = LegacyParamMapper.translate(
				ImmutableMap.<String, String>of("find_rankToReturn", "infraspec"));
		assertEquals("f_infraspecific", translated.get("f"));
	}
}