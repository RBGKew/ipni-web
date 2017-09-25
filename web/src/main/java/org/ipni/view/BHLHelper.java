package org.ipni.view;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Handlebars.SafeString;
import com.google.common.collect.ImmutableMap;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class BHLHelper {

	private static final Pattern TITLE = Pattern.compile("\\[BHL_title_id:(\\d+)\\]");
	private static final Pattern PAGE = Pattern.compile("\\[BHL_page_id:(\\d+)\\]");
	private static final String OPENURL = "http://www.biodiversitylibrary.org/openurl?pid=";
	private static final Logger logger = LoggerFactory.getLogger(BHLHelper.class);

	private String extract(Pattern pattern, String queryParam, String text, Options options) {
		Matcher match = pattern.matcher(text);
		StringBuilder ret = new StringBuilder();

		while(match.find()) {
			SafeString link = new SafeString(OPENURL + queryParam + ":" + match.group(1));
			try {
				ret.append(options.fn(ImmutableMap.<String, SafeString>of("link", link)));
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}

		return ret.toString();
	}

	public CharSequence hasBhlMarkers(String text, Options options) {
		StringBuilder ret = new StringBuilder();

		if(TITLE.matcher(text).matches() || PAGE.matcher(text).matches()) {
			try {
				ret.append(options.fn());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}

		return ret.toString();
	}

	public CharSequence withBhlPage(String text, Options options) {
		return new SafeString(extract(PAGE, "page", text, options));
	}

	public CharSequence withBhlTitle(String text, Options options) {
		return new SafeString(extract(TITLE, "title", text, options));
	}

	public CharSequence hasBhlMarkers(String text, Options options) {
		StringBuilder ret = new StringBuilder();
		logger.debug("Checking {} for BHL markers", text);

		if(TITLE.matcher(text).find() || PAGE.matcher(text).find()) {
			try {
				ret.append(options.fn());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}

		return ret.toString();
	}

	public CharSequence stripBhlMarkers(String text) {
		text = TITLE.matcher(text).replaceAll("");
		text = PAGE.matcher(text).replaceAll("");

		return text;
	}
}
