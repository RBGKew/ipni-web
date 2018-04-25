package org.ipni.legacy.model;

import static java.util.stream.Collectors.joining;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.stream.Stream;

import org.apache.solr.common.SolrDocument;
import org.ipni.model.Name;
import org.springframework.beans.BeanUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Delimited {

	protected final Name name;

	public Delimited(SolrDocument doc) {
		this(new Name(doc));
	}

	public Delimited(Name name) {
		this.name = name;
	}

	public String getHybridGenus() {
		return name.isHybridGenus() ? "Y" : "N";
	}

	public String getHybrid() {
		return name.isHybrid() ? "Y" : "N";
	}

	public String getFullName() {
		return String.format("%s %s",
				Objects.toString(name.getName(), ""),
				Objects.toString(name.getAuthors(), "")).trim();
	}

	public String getCollectionDateAsText() {
		return String.format("%s %s",
				Objects.toString(name.getCollectionDate1(), ""),
				Objects.toString(name.getCollectionDate2(), "")).trim();
	}

	protected abstract Stream<DelimitedField> orderedFields();

	public String toDelimited() {
		return orderedFields()
				.map(DelimitedField::field)
				.map(field -> extractProperty(field))
				.collect(joining("%", "", "\n"));
	}

	public String headers() {
		return orderedFields()
				.map(DelimitedField::display)
				.collect(joining("%", "", "\n"));
	}

	public String extractProperty(String field) {
		try {
			PropertyDescriptor delegate = BeanUtils.getPropertyDescriptor(Name.class, field);
			PropertyDescriptor override = BeanUtils.getPropertyDescriptor(this.getClass(), field);

			if(override != null && override.getReadMethod() != null) {
				log.trace("calling {}", override.getReadMethod().toString());
				return Objects.toString(override.getReadMethod().invoke(this), "");
			}

			if(delegate != null && delegate.getReadMethod() != null) {
				log.trace("calling {}", delegate.getReadMethod().toString());
				return Objects.toString(delegate.getReadMethod().invoke(this.name), "");
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		log.trace("{} not found. Setting as blank string", field);
		return "";
	}
}