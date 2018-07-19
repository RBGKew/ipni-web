package org.ipni.view;

import com.github.jknack.handlebars.Handlebars;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class Highlight {
	public CharSequence highlight(String value, String menu_item) {
		if (value != null && menu_item != null && value.contains(menu_item)) {
			return new Handlebars.SafeString("active");
		}
		return new Handlebars.SafeString("");
	}
}
