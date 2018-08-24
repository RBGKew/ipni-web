package org.ipni.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.solr.common.SolrDocument;
import org.ipni.constants.FieldMapping;
import org.ipni.util.CleanUtil;
import org.ipni.util.IdUtil;
import org.ipni.view.BHLHelper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@JsonInclude(Include.NON_NULL)
@Builder
@AllArgsConstructor
@Slf4j
public class Name {
	private String name;
	private String authors;
	private String publishingAuthor;
	private List<NameAuthor> authorTeam;
	private String originalRemarks;
	private String rank;
	private String url;
	private String family;
	private String genus;
	private String species;
	private String infraspecific;
	private List<Name> basionym;
	private List<Name> basionymOf;
	private String basionymStr;
	private String basionymAuthorStr;
	private String bibliographicReference;
	private String bibliographicTypeInfo;
	private String collectionNumber;
	private String collectorTeam;
	private String collectionDate1;
	private String collectionDate2;
	private String citationType;
	private List<Name> conservedAgainst;
	private List<Name> rejectedInFavorOf;
	private List<Name> correctionOf;
	private List<Name> correctedBy;
	private LocalDate dateCreated;
	private LocalDate dateLastModified;
	private String distribution;
	private String eastOrWest;
	private String geographicUnit;
	private boolean hybrid;
	private boolean hybridGenus;
	private List<Name> hybridParents;
	private String infrafamily;
	private String infragenus;
	private String infraspecies;
	private List<Name> isonym;
	private List<Name> isonymOf;
	private boolean inPowo;
	private List<Name> laterHomonymOf;
	private List<Name> hasLaterHomonym;
	private String latitudeDegrees;
	private String latitudeMinutes;
	private String latitudeSeconds;
	private String locality;
	private String longitudeDegrees;
	private String longitudeMinutes;
	private String longitudeSeconds;
	private String nameStatus;
	private String nameStatusType;
	private String nameStatusBotCode;
	private List<Name> nomenclaturalSynonym;
	private String northOrSouth;
	private String originalBasionym;
	private String originalBasionymAuthorTeam;
	private String originalHybridParentage;
	private String originalReplacedSynonym;
	private String originalReplacedSynonymAuthorTeam;
	private String originalTaxonDistribution;
	private String otherLinks;
	private String pageAsText;
	private Publication linkedPublication;
	private String publication;
	private Integer publicationYear;
	private String publicationYearNote;
	private String referenceCollation;
	private String publicationId;
	private String recordType;
	private String reference;
	private String referenceRemarks;
	private String remarks;
	private List<Name> replacedSynonym;
	private List<Name> replacedSynonymOf;
	private List<Name> sameCitationAs;
	private List<Name> duplicateCitationOf;
	private String speciesAuthor;
	private List<Name> superfluousNameOf;
	private List<Name> hasSuperfluousName;
	private boolean suppressed;
	private boolean topCopy;
	private List<Name> type;
	private List<Name> typeOf;
	private String typeChosenBy;
	private Coordinates typeCoordinates;
	private String typeLocations;
	private String typeName;
	private String typeNote;
	private String typeRemarks;
	private List<Name> validationOf;
	private List<Name> validatedBy;
	private String version;
	private List<Name> parent;
	private List<Name> child;
	private List<Name> orthographicVariantOf;
	private List<Name> hasOrthographicVariant;
	private String id;
	private String fqId;
	@JsonIgnore
	private Collation collation;

	public Name(SolrDocument name) {
		this.authors = (String) name.getFirstValue(FieldMapping.author.solrField());
		this.authorTeam = parseAuthorTeam(name);
		this.bibliographicReference = (String) name.getFirstValue(FieldMapping.bibliographicReference.solrField());
		this.bibliographicTypeInfo = (String) name.getFirstValue(FieldMapping.bibliographicTypeInfo.solrField());
		this.basionymStr = (String)name.get(FieldMapping.basionym.solrField());
		this.basionymAuthorStr = (String)name.get(FieldMapping.basionymAuthor.solrField());
		this.collectionNumber = (String) name.getFirstValue(FieldMapping.collectionNumber.solrField());
		this.collectorTeam = (String) name.getFirstValue(FieldMapping.collectorTeam.solrField());
		this.collectionDate1 = collectionDate(name, FieldMapping.collectionDay1.solrField(), FieldMapping.collectionMonth1.solrField(), FieldMapping.collectionYear1.solrField());
		this.collectionDate2 = collectionDate(name, FieldMapping.collectionDay2.solrField(), FieldMapping.collectionMonth2.solrField(), FieldMapping.collectionYear2.solrField());
		this.citationType = (String) name.get(FieldMapping.citationType.solrField());
		this.distribution = (String) name.getFirstValue(FieldMapping.distribution.solrField());
		this.family = (String) name.getFirstValue(FieldMapping.family.solrField());
		this.genus = (String) name.getFirstValue(FieldMapping.genus.solrField());
		this.geographicUnit = (String) name.getFirstValue(FieldMapping.geographicUnit.solrField());
		this.hybrid = (Boolean) name.getFirstValue(FieldMapping.hybrid.solrField());
		this.hybridGenus = (Boolean) name.getFirstValue(FieldMapping.hybridGenus.solrField());
		this.fqId = (String) name.getFirstValue("id");
		this.id = IdUtil.idPart(fqId);
		this.inPowo = (Boolean) name.getFirstValue(FieldMapping.inPowo.solrField());
		this.infrafamily = (String) name.getFirstValue(FieldMapping.infrafamily.solrField());
		this.infragenus = (String) name.getFirstValue(FieldMapping.infragenus.solrField());
		this.infraspecies = (String) name.getFirstValue(FieldMapping.infraspecies.solrField());
		this.locality = (String) name.getFirstValue(FieldMapping.locality.solrField());
		this.name = (String) name.get(FieldMapping.scientificName.solrField());
		this.nameStatus = (String) name.getFirstValue(FieldMapping.nameStatus.solrField());
		this.nameStatusType = (String) name.getFirstValue(FieldMapping.nameStatusEditorType.solrField());
		this.nameStatusBotCode = (String) name.getFirstValue(FieldMapping.nameStatusBotCodeType.solrField());
		this.originalBasionym = (String) name.getFirstValue(FieldMapping.originalBasionym.solrField());
		this.originalBasionymAuthorTeam = (String) name.getFirstValue(FieldMapping.originalBasionymAuthorTeam.solrField());
		this.originalHybridParentage = (String) name.getFirstValue(FieldMapping.originalHybridParentage.solrField());
		this.originalRemarks = CleanUtil.equalsPrefixedToNull((String) name.getFirstValue(FieldMapping.originalRemarks.solrField()));
		this.originalReplacedSynonym = (String) name.getFirstValue(FieldMapping.originalReplacedSynonym.solrField());
		this.originalReplacedSynonymAuthorTeam = (String) name.getFirstValue(FieldMapping.originalReplacedSynonymAuthorTeam.solrField());
		this.originalTaxonDistribution = (String) name.getFirstValue(FieldMapping.originalTaxonDistribution.solrField());
		this.otherLinks = (String) name.getFirstValue(FieldMapping.otherLinks.solrField());
		this.pageAsText = (String) name.getFirstValue(FieldMapping.pageAsText.solrField());
		this.publication = (String) name.getFirstValue(FieldMapping.publication.solrField());
		this.publicationYear = (Integer) name.getFirstValue(FieldMapping.yearPublished.solrField());
		this.publicationYearNote = CleanUtil.stripBrackets((String) name.getFirstValue(FieldMapping.publicationYearNote.solrField()));
		this.publicationId = IdUtil.idPart((String) name.getFirstValue(FieldMapping.publicationId.solrField()));
		this.publishingAuthor = (String) name.get(FieldMapping.publishingAuthor.solrField());
		this.referenceCollation = (String) name.getFirstValue(FieldMapping.referenceCollation.solrField());
		this.rank = (String) name.getFirstValue(FieldMapping.rank.solrField());
		this.recordType = "citation";
		this.reference = (String) name.getFirstValue(FieldMapping.reference.solrField());
		this.remarks = CleanUtil.stripEnglishInBrackets((String) name.getFirstValue(FieldMapping.remarks.solrField()));
		this.referenceRemarks = CleanUtil.stripLeadingPunctuation((String) name.getFirstValue(FieldMapping.referenceRemarks.solrField()));
		this.species = (String) name.getFirstValue(FieldMapping.species.solrField());
		this.speciesAuthor = (String) name.getFirstValue(FieldMapping.speciesAuthor.solrField());
		this.suppressed = (Boolean) name.get(FieldMapping.suppressed.solrField());
		this.topCopy = (Boolean) name.get(FieldMapping.topCopy.solrField());
		this.typeChosenBy = (String) name.getFirstValue(FieldMapping.typeChosenBy.solrField());
		this.typeCoordinates = buildCoordinates(name);
		this.typeLocations = (String) name.getFirstValue(FieldMapping.typeLocations.solrField());
		this.typeName = (String) name.getFirstValue(FieldMapping.typeName.solrField());
		this.typeNote = (String) name.getFirstValue(FieldMapping.typeNote.solrField());
		this.typeRemarks = (String) name.getFirstValue(FieldMapping.typeRemarks.solrField());
		this.url = "/n/" + id;
		this.version = (String) name.getFirstValue(FieldMapping.version.solrField());
		this.collation = new Collation(this.referenceCollation);
	}

	private String collectionDate(SolrDocument name, String dayField, String monthField, String yearField) {
		String dayStr = (String) name.getFirstValue(dayField);
		String monthStr = (String) name.getFirstValue(monthField);
		String yearStr = (String) name.getFirstValue(yearField);

		try {
			if (yearStr != null) {
				String format = "yyyy";
				int month = 1;
				int day = 1;

				if (monthStr != null) {
					month = Integer.parseInt(monthStr);
					format = "MMM " + format;
				}

				if (dayStr != null) {
					day = Integer.parseInt(dayStr);
					format = "d " + format;
				}

				return LocalDate.of(Integer.parseInt(yearStr), month, day).format(DateTimeFormatter.ofPattern(format));
			}
		} catch (DateTimeException | NumberFormatException e) {
			log.warn("Error parsing date: y{} m{} d{}", yearStr, monthStr, dayStr);
		}
		return null;
	}

	private Coordinates buildCoordinates(SolrDocument name) {
		Coordinates coordinates =  Coordinates.builder()
				.latitudeDegrees((String) name.getFirstValue(FieldMapping.latitudeDegrees.solrField()))
				.latitudeMinutes((String) name.getFirstValue(FieldMapping.latitudeMinutes.solrField()))
				.latitudeSeconds((String) name.getFirstValue(FieldMapping.latitudeSeconds.solrField()))
				.longitudeDegrees((String) name.getFirstValue(FieldMapping.longitudeDegrees.solrField()))
				.longitudeMinutes((String) name.getFirstValue(FieldMapping.longitudeMinutes.solrField()))
				.longitudeSeconds((String) name.getFirstValue(FieldMapping.longitudeSeconds.solrField()))
				.eastOrWest((String) name.getFirstValue(FieldMapping.eastOrWest.solrField()))
				.northOrSouth((String) name.getFirstValue(FieldMapping.northOrSouth.solrField()))
				.build();

		if(coordinates.isValid()) {
			return coordinates;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private List<NameAuthor> parseAuthorTeam(SolrDocument name) {
		if(name.get("detail_author_team_ids") == null) {
			return null;
		}

		return ((List<String>) name.get("detail_author_team_ids")).stream()
				.map(NameAuthor::new)
				.collect(Collectors.toList());
	}

	@JsonProperty
	public boolean hasNomenclaturalNotes() {
		return nameStatus != null || referenceRemarks != null;
	}

	@JsonProperty
	public boolean hasTypeData() {
		return bibliographicReference != null
				|| bibliographicTypeInfo != null
				|| collectorTeam != null
				|| distribution != null
				|| geographicUnit != null
				|| latitudeDegrees != null
				|| locality != null
				|| typeChosenBy != null
				|| typeLocations != null
				|| typeName != null;
	}

	@JsonProperty
	public boolean hasOriginalData() {
		return originalRemarks != null
				|| originalHybridParentage != null
				|| originalReplacedSynonym != null
				|| originalHybridParentage != null
				|| originalTaxonDistribution != null;
	}

	@JsonProperty
	public boolean hasLinks() {
		return basionym != null
				|| basionymOf != null
				|| conservedAgainst != null
				|| correctedBy != null
				|| correctionOf != null
				|| duplicateCitationOf != null
				|| hasLaterHomonym != null
				|| hasOrthographicVariant != null
				|| hybridParents != null
				|| isonym != null
				|| isonymOf != null
				|| laterHomonymOf != null
				|| nomenclaturalSynonym != null
				|| orthographicVariantOf != null
				|| rejectedInFavorOf != null
				|| replacedSynonym != null
				|| sameCitationAs != null
				|| superfluousNameOf != null
				|| type != null
				|| validatedBy != null
				|| validationOf != null;
	}

	@JsonProperty
	public String bhlLink() {
		return BHLHelper.buildNameLink(this, linkedPublication);
	}
}
