package org.ipni.view;

import com.github.jknack.handlebars.Handlebars;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class TextHelper {

	public CharSequence breakLines(String text) {
		return new Handlebars.SafeString(text.replaceAll(";", "<br/>"));
	}
}
