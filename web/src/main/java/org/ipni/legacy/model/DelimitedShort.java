package org.ipni.legacy.model;

import static org.ipni.legacy.model.DelimitedField.Authors;
import static org.ipni.legacy.model.DelimitedField.Basionym;
import static org.ipni.legacy.model.DelimitedField.BasionymAuthor;
import static org.ipni.legacy.model.DelimitedField.CitationType;
import static org.ipni.legacy.model.DelimitedField.Collation;
import static org.ipni.legacy.model.DelimitedField.Distribution;
import static org.ipni.legacy.model.DelimitedField.Family;
import static org.ipni.legacy.model.DelimitedField.FullNameWithoutFamilyAndAuthors;
import static org.ipni.legacy.model.DelimitedField.Genus;
import static org.ipni.legacy.model.DelimitedField.HybridGenus;
import static org.ipni.legacy.model.DelimitedField.Id;
import static org.ipni.legacy.model.DelimitedField.InfraFamily;
import static org.ipni.legacy.model.DelimitedField.InfraGenus;
import static org.ipni.legacy.model.DelimitedField.NameStatus;
import static org.ipni.legacy.model.DelimitedField.NomenclaturalSynonym;
import static org.ipni.legacy.model.DelimitedField.Publication;
import static org.ipni.legacy.model.DelimitedField.PublicationYearFull;
import static org.ipni.legacy.model.DelimitedField.PublishingAuthor;
import static org.ipni.legacy.model.DelimitedField.Rank;
import static org.ipni.legacy.model.DelimitedField.Remarks;
import static org.ipni.legacy.model.DelimitedField.ReplacedSynonym;
import static org.ipni.legacy.model.DelimitedField.Version;

import java.util.List;
import java.util.stream.Stream;

import org.apache.solr.common.SolrDocument;
import org.ipni.model.Name;

import com.google.common.collect.ImmutableList;

public class DelimitedShort extends Delimited {

	public static final List<DelimitedField> fields = ImmutableList.<DelimitedField>of(
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
			CitationType
			);

	public DelimitedShort(SolrDocument doc) {
		super(doc);
	}

	public DelimitedShort(Name name) {
		super(name);
	}

	@Override
	protected Stream<DelimitedField> orderedFields() {
		return fields.stream();
	}
}
