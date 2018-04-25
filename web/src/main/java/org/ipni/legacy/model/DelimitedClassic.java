package org.ipni.legacy.model;

import static org.ipni.legacy.model.DelimitedField.Authors;
import static org.ipni.legacy.model.DelimitedField.Basionym;
import static org.ipni.legacy.model.DelimitedField.BasionymAuthor;
import static org.ipni.legacy.model.DelimitedField.BibliographicReference;
import static org.ipni.legacy.model.DelimitedField.BibliographicTypeInfo;
import static org.ipni.legacy.model.DelimitedField.CitationType;
import static org.ipni.legacy.model.DelimitedField.Collation;
import static org.ipni.legacy.model.DelimitedField.CollectionDateAsText;
import static org.ipni.legacy.model.DelimitedField.CollectionDay1;
import static org.ipni.legacy.model.DelimitedField.CollectionDay2;
import static org.ipni.legacy.model.DelimitedField.CollectionMonth1;
import static org.ipni.legacy.model.DelimitedField.CollectionMonth2;
import static org.ipni.legacy.model.DelimitedField.CollectionNumber;
import static org.ipni.legacy.model.DelimitedField.CollectionYear1;
import static org.ipni.legacy.model.DelimitedField.CollectionYear2;
import static org.ipni.legacy.model.DelimitedField.CollectorTeamAsText;
import static org.ipni.legacy.model.DelimitedField.Distribution;
import static org.ipni.legacy.model.DelimitedField.EastOrWest;
import static org.ipni.legacy.model.DelimitedField.Family;
import static org.ipni.legacy.model.DelimitedField.FullName;
import static org.ipni.legacy.model.DelimitedField.FullNameWithoutAuthors;
import static org.ipni.legacy.model.DelimitedField.FullNameWithoutFamily;
import static org.ipni.legacy.model.DelimitedField.FullNameWithoutFamilyAndAuthors;
import static org.ipni.legacy.model.DelimitedField.Genus;
import static org.ipni.legacy.model.DelimitedField.GeographicUnitAsText;
import static org.ipni.legacy.model.DelimitedField.Hybrid;
import static org.ipni.legacy.model.DelimitedField.HybridGenus;
import static org.ipni.legacy.model.DelimitedField.HybridParents;
import static org.ipni.legacy.model.DelimitedField.Id;
import static org.ipni.legacy.model.DelimitedField.InfraFamily;
import static org.ipni.legacy.model.DelimitedField.InfraGenus;
import static org.ipni.legacy.model.DelimitedField.InfraSpecies;
import static org.ipni.legacy.model.DelimitedField.LatitudeDegrees;
import static org.ipni.legacy.model.DelimitedField.LatitudeMinutes;
import static org.ipni.legacy.model.DelimitedField.LatitudeSeconds;
import static org.ipni.legacy.model.DelimitedField.Locality;
import static org.ipni.legacy.model.DelimitedField.LongitudeDegrees;
import static org.ipni.legacy.model.DelimitedField.LongitudeMinutes;
import static org.ipni.legacy.model.DelimitedField.LongitudeSeconds;
import static org.ipni.legacy.model.DelimitedField.NameStatus;
import static org.ipni.legacy.model.DelimitedField.NomenclaturalSynonym;
import static org.ipni.legacy.model.DelimitedField.NorthOrSouth;
import static org.ipni.legacy.model.DelimitedField.OriginalBasionym;
import static org.ipni.legacy.model.DelimitedField.OriginalBasionymAuthorTeam;
import static org.ipni.legacy.model.DelimitedField.OriginalCitedType;
import static org.ipni.legacy.model.DelimitedField.OriginalHybridParentage;
import static org.ipni.legacy.model.DelimitedField.OriginalParentCitationTaxonNameAuthorTeam;
import static org.ipni.legacy.model.DelimitedField.OriginalRemarks;
import static org.ipni.legacy.model.DelimitedField.OriginalReplacedSynonym;
import static org.ipni.legacy.model.DelimitedField.OriginalReplacedSynonymAuthorTeam;
import static org.ipni.legacy.model.DelimitedField.OriginalTaxonDistribution;
import static org.ipni.legacy.model.DelimitedField.OriginalTaxonName;
import static org.ipni.legacy.model.DelimitedField.OriginalTaxonNameAuthorTeam;
import static org.ipni.legacy.model.DelimitedField.OtherLinks;
import static org.ipni.legacy.model.DelimitedField.Publication;
import static org.ipni.legacy.model.DelimitedField.PublishingAuthor;
import static org.ipni.legacy.model.DelimitedField.Rank;
import static org.ipni.legacy.model.DelimitedField.Reference;
import static org.ipni.legacy.model.DelimitedField.Remarks;
import static org.ipni.legacy.model.DelimitedField.ReplacedSynonym;
import static org.ipni.legacy.model.DelimitedField.Species;
import static org.ipni.legacy.model.DelimitedField.SpeciesAuthor;
import static org.ipni.legacy.model.DelimitedField.TypeLocations;
import static org.ipni.legacy.model.DelimitedField.TypeName;
import static org.ipni.legacy.model.DelimitedField.TypeRemarks;
import static org.ipni.legacy.model.DelimitedField.Version;

import java.util.List;
import java.util.stream.Stream;

import org.apache.solr.common.SolrDocument;
import org.ipni.model.Name;

import com.google.common.collect.ImmutableList;

public class DelimitedClassic extends Delimited {

	public static final List<DelimitedField> fields = ImmutableList.<DelimitedField>of(
			Id,
			Version,
			Family,
			InfraFamily,
			HybridGenus,
			Genus,
			InfraGenus,
			Hybrid,
			Species,
			SpeciesAuthor,
			InfraSpecies,
			Rank,
			Authors,
			BasionymAuthor,
			PublishingAuthor,
			FullName,
			FullNameWithoutFamily,
			FullNameWithoutAuthors,
			FullNameWithoutFamilyAndAuthors,
			Reference,
			Publication,
			Collation,
			Remarks,
			HybridParents,
			Basionym,
			ReplacedSynonym,
			Distribution,
			CitationType,
			BibliographicReference,
			BibliographicTypeInfo,
			CollectionDateAsText,
			CollectionDay1,
			CollectionMonth1,
			CollectionYear1,
			CollectionDay2,
			CollectionMonth2,
			CollectionYear2,
			CollectionNumber,
			CollectorTeamAsText,
			GeographicUnitAsText,
			Locality,
			LatitudeDegrees,
			LatitudeMinutes,
			LatitudeSeconds,
			NorthOrSouth,
			LongitudeDegrees,
			LongitudeMinutes,
			LongitudeSeconds,
			EastOrWest,
			TypeRemarks,
			TypeName,
			TypeLocations,
			NameStatus,
			OriginalTaxonName,
			OriginalTaxonNameAuthorTeam,
			OriginalReplacedSynonym,
			OriginalReplacedSynonymAuthorTeam,
			OriginalBasionym,
			OriginalBasionymAuthorTeam,
			OriginalParentCitationTaxonNameAuthorTeam,
			OriginalTaxonDistribution,
			OriginalHybridParentage,
			OriginalCitedType,
			OriginalRemarks,
			NomenclaturalSynonym,
			OtherLinks
			);

	public DelimitedClassic(SolrDocument doc) {
		super(doc);
	}

	public DelimitedClassic(Name name) {
		super(name);
	}

	@Override
	protected Stream<DelimitedField> orderedFields() {
		return fields.stream();
	}
}
