package org.ipni.model;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Collation {

	private String collation;

	/* 
	 * Matches the standardized collation form of "volume(issue): page" with some allowance for junk around it.
	 * Also matches single numbers, with or without a trailing . and makes the assumption that that is a page number
	 */ 
	private static final Pattern pattern = Pattern.compile("(?:\\D*(\\d+)\\s*(?:\\((\\d+(?:[-.]\\d)*)\\))?\\s*:\\s*(\\d+)\\D*.*$|^(\\d+)\\.?$)");

	private String volume;
	private String issue;
	private String page;
	private boolean parsed;

	public Collation(String collation) {
		this.collation = collation;
		if(collation != null) {
			Matcher matcher = pattern.matcher(collation);
			if(matcher.matches()) {
				this.volume = matcher.group(1);
				this.issue = matcher.group(2);
				// group(4) is the page number only variant, group(3) is when it is in a full collation
				this.page = matcher.group(3) == null ? matcher.group(4) : matcher.group(3);
				this.parsed = true;
			} else {
				this.parsed = false;
			}
		}
	}

	public Optional<String> getVolume() {
		return Optional.ofNullable(volume);
	}

	public Optional<String> getIssue() {
		return Optional.ofNullable(issue);
	}

	public Optional<String> getPage() {
		return Optional.ofNullable(page);
	}

	public boolean isParsed() {
		return parsed;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(collation);
		sb.append(" [ volume: ");
		sb.append(getVolume());
		sb.append(", issue: ");
		sb.append(getIssue());
		sb.append(", page: ");
		sb.append(getPage());
		sb.append(" ]");

		return sb.toString();
	}
}

