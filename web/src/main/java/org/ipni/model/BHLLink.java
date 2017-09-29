package org.ipni.model;

import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.base.Joiner;

public class BHLLink {

	private static final String OPENURL = "http://www.biodiversitylibrary.org/openurl?";

	private SortedMap<String, String> params;

	public BHLLink() {
		params = new TreeMap<>();
		params.put("url_ver", "z39.88-2004");
		params.put("ctx_ver", "Z39.88-2004");
		params.put("rft_val_fmt", "info:ofi/fmt:kev:mtx:book");
	}

	public BHLLink withTitleId(String titleId) {
		if(titleId != null) {
			params.put("rft_id", "http://www.biodiversitylibrary.org/bibliography/" + titleId);
		}

		return this;
	}

	public BHLLink withPageId(String pageId) {
		if(pageId != null) {
			params.put("rft_id", "http://www.biodiversitylibrary.org/page/" + pageId);
		}

		return this;
	}

	public BHLLink withVolume(String volume) {
		if(volume != null) {
			params.put("rft.volume", volume);
		}

		return this;
	}

	public BHLLink withIssue(String issue) {
		if(issue == null) return this;

		// the bhl resolver doesn't understand issue ranges so just leave it out
		if(issue.contains("-")) {
			return this;
		}

		// some issues are in ipni with a . (e.g., 2.4). These can have the decimal part truncated
		if(issue.contains(".")) {
			issue = issue.substring(0, issue.indexOf("."));
		}

		params.put("rft.issue", issue);
		return this;
	}

	public BHLLink withPage(String page) {
		if(page != null) {
			params.put("rft.spage", page);
		}

		return this;
	}

	public BHLLink withYear(Integer year) {
		if(year != null) {
			params.put("rft.date", year.toString());
		}

		return this;
	}

	public BHLLink withCollation(Collation collation) {
		if(collation == null) return this;

		if(collation.getVolume().isPresent()) {
			withVolume(collation.getVolume().get());
		}

		if(collation.getIssue().isPresent()) {
			withIssue(collation.getIssue().get());
		}

		if(collation.getPage().isPresent()) {
			withPage(collation.getPage().get());
		}

		return this;
	}

	public String build() {
		return String.format("%s%s", OPENURL, Joiner.on("&").withKeyValueSeparator("=").join(params));
	}
}
