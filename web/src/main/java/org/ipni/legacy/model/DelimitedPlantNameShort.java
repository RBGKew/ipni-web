package org.ipni.legacy.model;

import static org.ipni.legacy.model.DelimitedPlantNameField.*;

import java.util.List;
import java.util.stream.Stream;

import org.apache.solr.common.SolrDocument;
import org.ipni.model.Name;

import com.google.common.collect.ImmutableList;

public class DelimitedPlantNameShort extends DelimitedPlantName {
	public static final List<DelimitedPlantNameField> fields = ImmutableList.<DelimitedPlantNameField>of(
			Id,
			Version,
			Family,
			InfraFamily,
			HybridGenus,
			Genus,
			InfraGenus,
			Rank,
			Authors,
			BasionymAuthor,
			PublishingAuthor,
			FullNameWithoutFamilyAndAuthors,
			Publication,
			Collation,
			PublicationYearFull,
			NameStatus,
			Remarks,
			Basionym,
			ReplacedSynonym,
			NomenclaturalSynonym,
			Distribution,
			CitationType);

	public DelimitedPlantNameShort(SolrDocument doc) {
		super(doc);
	}

	public DelimitedPlantNameShort(Name name) {
		super(name);
	}

	@Override
	protected Stream<DelimitedPlantNameField> orderedFields() {
		return fields.stream();
	}
}