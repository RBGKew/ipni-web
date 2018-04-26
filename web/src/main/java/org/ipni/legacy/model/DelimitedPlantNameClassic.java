package org.ipni.legacy.model;

import static org.ipni.legacy.model.DelimitedPlantNameField.*;

import java.util.List;
import java.util.stream.Stream;

import org.apache.solr.common.SolrDocument;
import org.ipni.model.Name;

import com.google.common.collect.ImmutableList;

public class DelimitedPlantNameClassic extends DelimitedPlantName {

	public static final List<DelimitedPlantNameField> fields = ImmutableList.<DelimitedPlantNameField>of(
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
			OtherLinks);

	public DelimitedPlantNameClassic(SolrDocument doc) {
		super(doc);
	}

	public DelimitedPlantNameClassic(Name name) {
		super(name);
	}

	@Override
	protected Stream<DelimitedPlantNameField> orderedFields() {
		return fields.stream();
	}
}
