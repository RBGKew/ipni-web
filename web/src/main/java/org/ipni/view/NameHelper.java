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

		if(hasReference(citation)) {
			name.append(", <small>");

			if(citation.getPublication() != null) {
				name.append(citation.getPublication());
				name.append(" ");
			}

			if(citation.getReferenceCollation() != null) {
				name.append(citation.getReferenceCollation());
			}

			if(citation.getPublicationYear() != null) {
				name.append(" (");
				name.append(citation.getPublicationYear());
				name.append(")");
			}

			name.append(".</small>");
		}

		return new Handlebars.SafeString(name);
	}

	public CharSequence linkedFullName(Name citation) {
		if(citation == null) return null;
		StringBuffer name = new StringBuffer();

		name.append("<h2><i>");
		name.append(showName(citation));
		name.append("</i> ");

		if(citation.getAuthors() != null) {
			AuthorHelper authorHelper = new AuthorHelper();
			name.append(authorHelper.linkAuthor(citation));
		}

		name.append("</h2>");

		if(hasReference(citation)) {
			name.append(", ");
			if(citation.getPublication() != null) {
				if(citation.getPublicationId() != null) {
					name.append("<a href=\"/");
					name.append(citation.getPublicationId());
					name.append("\" class=\"publication-link\">");
					name.append(citation.getPublication());
					name.append("</a> ");
				} else {
					name.append(citation.getPublication());
					name.append(" ");
				}
			}

			if(citation.getReferenceCollation() != null) {
				name.append(" ");
				name.append(citation.getReferenceCollation());
			}

			if(citation.getPublicationYear() != null) {
				name.append(" (");
				name.append(citation.getPublicationYear());
				name.append(")");
			}

			if(name.charAt(name.length() - 1) != '.') {
				name.append(".");
			}
		}

		return new Handlebars.SafeString(name);
	}

	private boolean hasReference(Name citation) {
		return !(citation.getPublication() == null
				&& citation.getReferenceCollation() == null
				&& citation.getPublicationYear() == null);
	}
}
