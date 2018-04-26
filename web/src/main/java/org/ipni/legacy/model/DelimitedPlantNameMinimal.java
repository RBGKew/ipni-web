package org.ipni.legacy.model;

import static org.ipni.legacy.model.DelimitedPlantNameField.Authors;
import static org.ipni.legacy.model.DelimitedPlantNameField.Family;
import static org.ipni.legacy.model.DelimitedPlantNameField.FullNameWithoutFamilyAndAuthors;
import static org.ipni.legacy.model.DelimitedPlantNameField.Id;
import static org.ipni.legacy.model.DelimitedPlantNameField.Version;

import java.util.List;
import java.util.stream.Stream;

import org.apache.solr.common.SolrDocument;
import org.ipni.model.Name;

import com.google.common.collect.ImmutableList;

public class DelimitedPlantNameMinimal extends DelimitedPlantName {

	public static final List<DelimitedPlantNameField> fields = ImmutableList.<DelimitedPlantNameField>of(
			Id,
			Version,
			Family,
			FullNameWithoutFamilyAndAuthors,
			Authors);

	public DelimitedPlantNameMinimal(SolrDocument doc) {
		super(doc);
	}

	public DelimitedPlantNameMinimal(Name name) {
		super(name);
	}

	@Override
	protected Stream<DelimitedPlantNameField> orderedFields() {
		return fields.stream();
	}
}
