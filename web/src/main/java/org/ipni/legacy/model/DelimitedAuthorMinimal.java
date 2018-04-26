package org.ipni.legacy.model;

import static org.ipni.legacy.model.DelimitedAuthorField.DefaultAuthorForename;
import static org.ipni.legacy.model.DelimitedAuthorField.DefaultAuthorSurname;
import static org.ipni.legacy.model.DelimitedAuthorField.Id;
import static org.ipni.legacy.model.DelimitedAuthorField.StandardForm;
import static org.ipni.legacy.model.DelimitedAuthorField.Version;

import java.util.List;
import java.util.stream.Stream;

import org.apache.solr.common.SolrDocument;
import org.ipni.model.Author;

import com.google.common.collect.ImmutableList;

public class DelimitedAuthorMinimal extends Delimited<Author> {
	public static final List<DelimitedAuthorField> fields = ImmutableList.<DelimitedAuthorField>of(
			Id,
			Version,
			StandardForm,
			DefaultAuthorForename,
			DefaultAuthorSurname);

	public DelimitedAuthorMinimal(Author delegate) {
		super(delegate, Author.class);
	}

	public DelimitedAuthorMinimal(SolrDocument doc) {
		this(new Author(doc));
	}

	@Override
	protected Stream<DelimitedAuthorField> orderedFields() {
		return fields.stream();
	}
}