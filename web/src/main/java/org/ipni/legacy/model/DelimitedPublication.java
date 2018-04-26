package org.ipni.legacy.model;

import static org.ipni.legacy.model.DelimitedPublicationField.*;

import java.util.List;
import java.util.stream.Stream;

import org.apache.solr.common.SolrDocument;
import org.ipni.model.Publication;

import com.google.common.collect.ImmutableList;

public class DelimitedPublication extends Delimited<Publication> {
	public static final List<DelimitedPublicationField> fields = ImmutableList.<DelimitedPublicationField>of(
			Id,
			Version,
			Abbreviation,
			Title,
			Remarks,
			BPHNumber,
			ISBN,
			ISSN,
			AuthorsRole,
			Edition,
			InPublicationFacade,
			Date,
			LCNumber,
			Place,
			PublicationAuthorTeam,
			PrecededBy,
			TL2Author,
			TL2Number,
			TDWGAbbreviation);

	public DelimitedPublication(Publication delegate) {
		super(delegate, Publication.class);
	}

	public DelimitedPublication(SolrDocument doc) {
		this(new Publication(doc));
	}

	@Override
	protected Stream<DelimitedPublicationField> orderedFields() {
		return fields.stream();
	}
}