package org.ipni.legacy.model;

import static java.util.stream.Collectors.joining;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Delimited<T> {

	protected T delegate;
	final Class<T> delegateClass;

	protected abstract Stream<? extends DelimitedField> orderedFields();

	public Delimited(T delegate, Class<T> delegateClass) {
		this.delegate = delegate;
		this.delegateClass = delegateClass;
	}

	public String toDelimited() {
		return orderedFields()
				.map(DelimitedField::getField)
				.map(field -> extractProperty(field))
				.collect(joining("%", "", "\n"));
	}

	public String headers() {
		return orderedFields()
				.map(DelimitedField::getDisplay)
				.collect(joining("%", "", "\n"));
	}

	public String extractProperty(String field) {
		try {
			PropertyDescriptor delegate = BeanUtils.getPropertyDescriptor(delegateClass, field);
			PropertyDescriptor override = BeanUtils.getPropertyDescriptor(this.getClass(), field);

			if(override != null && override.getReadMethod() != null) {
				log.trace("calling {}", override.getReadMethod().toString());
				return Objects.toString(override.getReadMethod().invoke(this), "");
			}

			if(delegate != null && delegate.getReadMethod() != null) {
				log.trace("calling {}", delegate.getReadMethod().toString());
				return Objects.toString(delegate.getReadMethod().invoke(this.delegate), "");
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		log.trace("{} not found. Setting as blank string", field);
		return "";
	}
}