package org.ipni.legacy.model;

import static org.ipni.legacy.model.DelimitedAuthorField.*;

import java.util.List;
import java.util.stream.Stream;

import org.apache.solr.common.SolrDocument;
import org.ipni.model.Author;

import com.google.common.collect.ImmutableList;

public class DelimitedAuthorExtended extends Delimited<Author> {
	public static final List<DelimitedAuthorField> fields = ImmutableList.<DelimitedAuthorField>of(
			Id,
			Version,
			StandardForm,
			DefaultAuthorForename,
			DefaultAuthorSurname,
			TaxonGroups,
			Dates,
			AlternativeNames
			);

	public DelimitedAuthorExtended(Author delegate) {
		super(delegate, Author.class);
	}

	public DelimitedAuthorExtended(SolrDocument doc) {
		this(new Author(doc));
	}

	@Override
	protected Stream<DelimitedAuthorField> orderedFields() {
		return fields.stream();
	}
}