package org.ipni.view;

import org.ipni.model.Name;

import com.github.jknack.handlebars.Handlebars;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class NameHelper {
	public CharSequence showName(Name citation) {
		if(citation == null) return null;

		if(citation.getName() != null) {
			return citation.getName();
		} else {
			return citation.getFamily();
		}
	}

	public CharSequence fullName(Name citation) {
		if(citation == null) return null;

		StringBuffer name = new StringBuffer();
		name.append("<i>");
		name.append(showName(citation));
		name.append("</i> ");

		if(citation.getAuthors() != null) {
			name.append(citation.getAuthors());
		}

		if(citation.getReference() != null) {
			name.append(", <small>");
			name.append(citation.getReference());
			name.append("</small>");
		}

		return new Handlebars.SafeString(name);
	}
}
