package org.ipni.legacy.model;

import java.util.Objects;
import java.util.stream.Stream;

import org.apache.solr.common.SolrDocument;
import org.ipni.model.Name;

public abstract class DelimitedPlantName extends Delimited<Name> {

	public DelimitedPlantName(Name name) {
		super(name, Name.class);
	}

	public DelimitedPlantName(SolrDocument doc) {
		this(new Name(doc));
	}

	public String getHybridGenus() {
		return delegate.isHybridGenus() ? "Y" : "N";
	}

	public String getHybrid() {
		return delegate.isHybrid() ? "Y" : "N";
	}

	public String getFullName() {
		return String.format("%s %s",
				Objects.toString(delegate.getName(), ""),
				Objects.toString(delegate.getAuthors(), "")).trim();
	}

	public String getCollectionDateAsText() {
		return String.format("%s %s",
				Objects.toString(delegate.getCollectionDate1(), ""),
				Objects.toString(delegate.getCollectionDate2(), "")).trim();
	}

	protected abstract Stream<DelimitedPlantNameField> orderedFields();
}
