package org.ipni.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NameAuthor {

	private String name;
	private String id;
	private int order;
	private String type;

	/*
	 * Takes a names string in the format [name]@[id]@[order]@[type].
	 * E.g., X.Y.Wang@37842-1@4@autEx
	 */
	public NameAuthor(String nameAuthorString) {
		String[] token = nameAuthorString.split("@");

		if (token.length != 4) {
			throw new IllegalArgumentException("Invalid name author string: " + nameAuthorString);
		}

		name = token[0];
		id = parseId(token[1]);
		order = Integer.parseInt(token[2]);
		type = token[3];
	}

	public String getUrl() {
		if(id == null) {
			return null;
		} else {
			return "/a/" + id;
		}
	}

	private String parseId(String id) {
		// null ids are denoted as 0-null or 0-0
		if("0-null".equals(id) || "0-0".equals(id)) {
			return null;
		} else {
			return id;
		}
	}
}
