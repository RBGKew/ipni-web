package org.ipni.model;

import java.time.LocalDate;
import java.util.List;

import org.apache.solr.common.SolrDocument;
import org.ipni.constants.FieldMapping;
import org.ipni.util.CleanUtil;
import org.ipni.view.BHLHelper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@Builder
@AllArgsConstructor
public class Name {
	private String name;
	private String authors;
	private List<NameAuthor> authorTeam;
	private String originalRemarks;
	private String rank;
	private String url;
	private String family;
	private String genus;
	private String species;
	private String infraspecific;
	private List<Name> basionym;
	private String bibliographicReference;
	private String bibliographicTypeInfo;
	private String collectionNumber;
	private String collectorTeam;
	private String collectionDate1;
	private String collectionDate2;
	private List<Name> conservedAgainst;
	private List<Name> correctionOf;
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
	private List<Name> isonymOf;
	private boolean inPowo;
	private List<Name> laterHomonymOf;
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
	private String remarks;
	private List<Name> replacedSynonym;
	private List<Name> sameCitationAs;
	private String speciesAuthor;
	private List<Name> superfluousNameOf;
	private boolean suppressed;
	private boolean topCopy;
	private Name type;
	private String typeLocations;
	private String typeName;
	private String typeRemarks;
	private List<Name> validationOf;
	private String version;
	private List<Name> parent;
	private List<Name> orthographicVariantOf;
	private String id;
	@JsonIgnore
	private Collation collation;

	public Name(SolrDocument name) {
		this.authors = (String) name.getFirstValue(FieldMapping.author.solrField());
		this.bibliographicReference = (String) name.getFirstValue(FieldMapping.bibliographicReference.solrField());
		this.bibliographicTypeInfo = (String) name.getFirstValue(FieldMapping.bibliographicTypeInfo.solrField());
		this.collectionNumber = (String) name.getFirstValue(FieldMapping.collectionNumber.solrField());
		this.collectorTeam = (String) name.getFirstValue(FieldMapping.collectorTeam.solrField());
		this.collectionDate1 = collectionDate(name, FieldMapping.collectionDay1.solrField(), FieldMapping.collectionMonth1.solrField(), FieldMapping.collectionYear1.solrField());
		this.collectionDate2 = collectionDate(name, FieldMapping.collectionDay2.solrField(), FieldMapping.collectionMonth2.solrField(), FieldMapping.collectionYear2.solrField());
		this.distribution = (String) name.getFirstValue(FieldMapping.distribution.solrField());
		this.eastOrWest = (String) name.getFirstValue(FieldMapping.eastOrWest.solrField());
		this.family = (String) name.getFirstValue(FieldMapping.family.solrField());
		this.genus = (String) name.getFirstValue(FieldMapping.genus.solrField());
		this.geographicUnit = (String) name.getFirstValue(FieldMapping.geographicUnit.solrField());
		this.hybrid = (Boolean) name.getFirstValue(FieldMapping.hybrid.solrField());
		this.hybridGenus = (Boolean) name.getFirstValue(FieldMapping.hybridGenus.solrField());
		this.id = (String) name.getFirstValue("id");
		this.inPowo = (Boolean) name.getFirstValue(FieldMapping.inPowo.solrField());
		this.infrafamily = (String) name.getFirstValue(FieldMapping.infrafamily.solrField());
		this.infragenus = (String) name.getFirstValue(FieldMapping.infragenus.solrField());
		this.infraspecies = (String) name.getFirstValue(FieldMapping.infraspecies.solrField());
		this.latitudeDegrees = CleanUtil.zeroToNull((String) name.getFirstValue(FieldMapping.latitudeDegrees.solrField()));
		this.latitudeMinutes = CleanUtil.zeroToNull((String) name.getFirstValue(FieldMapping.latitudeMinutes.solrField()));
		this.latitudeSeconds = CleanUtil.zeroToNull((String) name.getFirstValue(FieldMapping.latitudeSeconds.solrField()));
		this.locality = (String) name.getFirstValue(FieldMapping.locality.solrField());
		this.longitudeDegrees = CleanUtil.zeroToNull((String) name.getFirstValue(FieldMapping.longitudeDegrees.solrField()));
		this.longitudeMinutes = CleanUtil.zeroToNull((String) name.getFirstValue(FieldMapping.longitudeMinutes.solrField()));
		this.longitudeSeconds = CleanUtil.zeroToNull((String) name.getFirstValue(FieldMapping.longitudeSeconds.solrField()));
		this.name = (String) name.get(FieldMapping.scientificName.solrField());
		this.nameStatus = (String) name.getFirstValue(FieldMapping.nameStatus.solrField());
		this.nameStatusType = (String) name.getFirstValue(FieldMapping.nameStatusEditorType.solrField());
		this.nameStatusBotCode = (String) name.getFirstValue(FieldMapping.nameStatusBotCodeType.solrField());
		this.northOrSouth = (String) name.getFirstValue(FieldMapping.northOrSouth.solrField());
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
		this.publicationId = (String) name.getFirstValue(FieldMapping.publicationId.solrField());
		this.referenceCollation = (String) name.getFirstValue(FieldMapping.referenceCollation.solrField());
		this.rank = (String) name.getFirstValue(FieldMapping.rank.solrField());
		this.recordType = "citation";
		this.reference = (String) name.getFirstValue(FieldMapping.reference.solrField());
		this.remarks = CleanUtil.stripThingsInBrackets((String) name.getFirstValue(FieldMapping.remarks.solrField()));
		this.species = (String) name.getFirstValue(FieldMapping.species.solrField());
		this.speciesAuthor = (String) name.getFirstValue(FieldMapping.speciesAuthor.solrField());
		this.suppressed = (Boolean) name.get(FieldMapping.suppressed.solrField());
		this.topCopy = (Boolean) name.get(FieldMapping.topCopy.solrField());
		this.typeLocations = (String) name.getFirstValue(FieldMapping.typeLocations.solrField());
		this.typeName = (String) name.getFirstValue(FieldMapping.typeName.solrField());
		this.typeRemarks = (String) name.getFirstValue(FieldMapping.typeRemarks.solrField());
		this.url = "/" + id;
		this.version = (String) name.getFirstValue(FieldMapping.version.solrField());
		this.collation = new Collation(this.referenceCollation);
	}

	private String collectionDate(SolrDocument name, String dayField, String monthField, String yearField) {
		String day = (String) name.getFirstValue(dayField);
		String month = (String) name.getFirstValue(monthField);
		String year = (String) name.getFirstValue(yearField);
		if(day != null && month != null && year != null){
			return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)).toString();
		}
		return null;
	}

	@JsonProperty
	public boolean hasTypeData() {
		return typeName != null
				|| bibliographicTypeInfo != null
				|| typeLocations != null
				|| geographicUnit != null
				|| locality != null
				|| latitudeDegrees != null
				|| collectorTeam != null
				|| bibliographicReference != null;
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
				|| conservedAgainst != null
				|| correctionOf != null
				|| isonymOf != null
				|| laterHomonymOf != null
				|| nomenclaturalSynonym != null
				|| replacedSynonym != null
				|| sameCitationAs != null
				|| superfluousNameOf != null
				|| validationOf != null
				|| orthographicVariantOf != null;
	}

	@JsonProperty
	public String bhlLink() {
		return BHLHelper.buildNameLink(this, linkedPublication);
	}
}
