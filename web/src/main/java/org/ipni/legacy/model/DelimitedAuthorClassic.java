package org.ipni.legacy.model;

import static org.ipni.legacy.model.DelimitedAuthorField.*;

import java.util.List;
import java.util.stream.Stream;

import org.apache.solr.common.SolrDocument;
import org.ipni.model.Author;

import com.google.common.collect.ImmutableList;

public class DelimitedAuthorClassic extends Delimited<Author> {
	public static final List<DelimitedAuthorField> fields = ImmutableList.<DelimitedAuthorField>of(
			Id,
			Version,
			DefaultAuthorName,
			DefaultAuthorForename,
			DefaultAuthorSurname,
			StandardForm,
			NameNotes,
			NameSource,
			Dates,
			DateTypeCode,
			DateTypeString,
			AlternativeAbbreviations,
			AlternativeNames,
			TaxonGroups,
			ExamplesOfNamePublished);

	public DelimitedAuthorClassic(Author delegate) {
		super(delegate, Author.class);
	}

	public DelimitedAuthorClassic(SolrDocument doc) {
		this(new Author(doc));
	}

	@Override
	protected Stream<DelimitedAuthorField> orderedFields() {
		return fields.stream();
	}
}