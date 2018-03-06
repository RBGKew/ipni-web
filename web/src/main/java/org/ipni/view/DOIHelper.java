package org.ipni.view;

import com.github.jknack.handlebars.Handlebars;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class DOIHelper {

	private static final String pattern = "doi:(\\S+)";
	private static final String replacement = "<a href=\"https://doi.org/$1\" class=\"doi-link\" data-doi=\"$1\" target=\"_blank\">$0</a>";

	public CharSequence linkDoi(String text) {
		return new Handlebars.SafeString(text.replaceAll(pattern, replacement));
	}
}
