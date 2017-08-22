package org.ipni.search;

import org.ipni.constants.FieldMapping;

public class Range {
	private enum Direction { Forward, Backward };

	private String field;
	private String value;
	private Direction direction;

	public Range(String field, String value) {
		this.field = extract(field);
		this.value = value;
		this.direction = direction(field);
	}

	public String build() {
		String solrRange = null;

		if(valid()) {
			String rangeTemplate;
			if(direction == Direction.Forward) {
				rangeTemplate = "%s:[%s TO *]";
			} else {
				rangeTemplate = "%s:[* TO %s]";
			}

			solrRange = String.format(rangeTemplate, FieldMapping.getSolrFieldFrom(field), value);
		}

		return solrRange;
	}

	public boolean valid() {
		return value != null
				&& !value.isEmpty()
				&& FieldMapping.isValidField(field);
	}

	private Direction direction(String field) {
		if(field.endsWith("after")) {
			return Direction.Forward;
		}

		if(field.endsWith("before")) {
			return Direction.Backward;
		}

		return null;
	}

	private String extract(String field) {
		field = field.replace(" after", "");
		field = field.replace(" before", "");
		return field;
	}
}
