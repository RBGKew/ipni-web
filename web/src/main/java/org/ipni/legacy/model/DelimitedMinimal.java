package org.ipni.legacy.model;

import static org.ipni.legacy.model.DelimitedField.Authors;
import static org.ipni.legacy.model.DelimitedField.Family;
import static org.ipni.legacy.model.DelimitedField.FullNameWithoutFamilyAndAuthors;
import static org.ipni.legacy.model.DelimitedField.Id;
import static org.ipni.legacy.model.DelimitedField.Version;

import java.util.List;
import java.util.stream.Stream;

import org.apache.solr.common.SolrDocument;
import org.ipni.model.Name;

import com.google.common.collect.ImmutableList;

public class DelimitedMinimal extends Delimited {

	public static final List<DelimitedField> fields = ImmutableList.<DelimitedField>of(
			Id,
			Version,
			Family,
			FullNameWithoutFamilyAndAuthors,
			Authors
			);

	public DelimitedMinimal(SolrDocument doc) {
		super(doc);
	}

	public DelimitedMinimal(Name name) {
		super(name);
	}

	@Override
	protected Stream<DelimitedField> orderedFields() {
		return fields.stream();
	}
}
