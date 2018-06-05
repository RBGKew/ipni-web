package org.ipni.constants;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.ipni.model.Name;

import lombok.Getter;

@Getter
public enum LinkType {
	Basionym("lookup_basionym_id",
			"basionym_of_id",
			Name::setBasionym,
			Name::setBasionymOf,
			Name::getBasionym,
			Name::getBasionymOf),

	Conservation("lookup_conserved_against_id",
			"rejected_in_favour_of_id",
			Name::setConservedAgainst,
			Name::setRejectedInFavorOf,
			Name::getConservedAgainst,
			Name::getRejectedInFavorOf),

	Correction("lookup_correction_of_id",
			"corrected_by_id",
			Name::setCorrectionOf,
			Name::setCorrectedBy,
			Name::getCorrectionOf,
			Name::getCorrectedBy),

	Isonym("lookup_isonym_of_id",
			"isonym_id",
			Name::setIsonymOf,
			Name::setIsonym,
			Name::getIsonymOf,
			Name::getIsonym),

	LaterHomonym("lookup_later_homonym_of_id",
			"has_later_homonym_id",
			Name::setLaterHomonymOf,
			Name::setHasLaterHomonym,
			Name::getLaterHomonymOf,
			Name::getHasLaterHomonym),

	NomenclaturalSynonym("lookup_nomenclatural_synonym_id",
			"nomenclatural_synonym_id",
			Name::setNomenclaturalSynonym,
			Name::setNomenclaturalSynonym,
			Name::getNomenclaturalSynonym,
			Name::getNomenclaturalSynonym),

	OrthographicVariant("lookup_orthographic_variant_of_id",
			"has_orthographic_variant_id",
			Name::setOrthographicVariantOf,
			Name::setHasOrthographicVariant,
			Name::getOrthographicVariantOf,
			Name::getHasOrthographicVariant),

	Parent("lookup_parent_id",
			"child_id",
			Name::setParent,
			Name::setChild,
			Name::getParent,
			Name::getChild),

	HybridParent("lookup_hybrid_parent_id",
			"lookup_hybrid_parent_id",
			Name::setHybridParents,
			Name::setHybridParents,
			Name::getHybridParents,
			Name::getHybridParents),

	ReplacedSynonym("lookup_replaced_synonym_id",
			"replaced_synonym_of_id",
			Name::setReplacedSynonym,
			Name::setReplacedSynonymOf,
			Name::getReplacedSynonym,
			Name::getReplacedSynonymOf),

	SameCitation("lookup_same_citation_as_id",
			"duplicate_citation_of_id",
			Name::setSameCitationAs,
			Name::setDuplicateCitationOf,
			Name::getSameCitationAs,
			Name::getDuplicateCitationOf),

	SuperfluousName("lookup_superfluous_name_of_id",
			"has_superfluous_name_id",
			Name::setSuperfluousNameOf,
			Name::setHasSuperfluousName,
			Name::getSuperfluousNameOf,
			Name::getHasSuperfluousName),

	Validation("lookup_validation_of_id",
			"validated_by_id",
			Name::setValidationOf,
			Name::setValidatedBy,
			Name::getValidationOf,
			Name::getValidatedBy),

	Type("lookup_type_id",
			"lookup_type_id",
			Name::setType,
			Name::setTypeOf,
			Name::getType,
			Name::getTypeOf);

	private String forwardField;
	private String reverseField;
	private BiConsumer<Name, List<Name>> forwardSetter;
	private BiConsumer<Name, List<Name>> reverseSetter;
	private Function<Name, List<Name>> forwardGetter;
	private Function<Name, List<Name>> reverseGetter;

	private LinkType(String forward, String reverse,
			BiConsumer<Name, List<Name>> forwardSetter,
			BiConsumer<Name, List<Name>> reverseSetter,
			Function<Name, List<Name>> forwardGetter,
			Function<Name, List<Name>> reverseGetter) {
		this.forwardField = forward;
		this.reverseField = reverse;
		this.forwardSetter = forwardSetter;
		this.reverseSetter = reverseSetter;
		this.forwardGetter = forwardGetter;
		this.reverseGetter = reverseGetter;
	}
}
