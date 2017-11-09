package org.ipni.view;

import org.ipni.model.Name;

import com.github.jknack.handlebars.Handlebars;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class NameHelper {

	private static final String[] ranks = {
			"[infrafam.unranked]",
			"[infragen.unranked]",
			"[infrasp.unranked]",
			"[infragen.grex]",
			"c.[infragen.]",
			"infragen.grex",
			"nothosubsect.",
			"nothosubtrib.",
			"supersubtrib.",
			"nothosubgen.",
			"[infragen.]",
			"nothosubsp.",
			"subsubforma",
			"grex_sect.",
			"[infragen]",
			"nothosect.",
			"subgenitor",
			"subsubvar.",
			"supersect.",
			"supertrib.",
			"suprasect.",
			"agamovar.",
			"[epsilon]",
			"gen. ser.",
			"microgen.",
			"nothogrex",
			"nothoser.",
			"nothovar.",
			"superser.",
			"agamosp.",
			"[alpha].",
			"[gamma].",
			"subhybr.",
			"subsect.",
			"subspec.",
			"subtrib.",
			"agglom.",
			"[beta].",
			"convar.",
			"genitor",
			"monstr.",
			"nothof.",
			"subfam.",
			"subgen.",
			"sublus.",
			"subser.",
			"subvar.",
			"f.juv.",
			"Gruppe",
			"modif.",
			"proles",
			"stirps",
			"subsp.",
			"cycl.",
			"forma",
			"group",
			"linea",
			"prol.",
			"sect.",
			"spec.",
			"subf.",
			"trib.",
			"fam.",
			"gen.",
			"grex",
			"lus.",
			"mut.",
			"oec.",
			"psp.",
			"race",
			"ser.",
			"var.",
			"ap.",
			"II.",
			"nm.",
			"2.",
			"A.",
			"B.",
			"C.",
			"D.",
			"E.",
			"f.",
			"G.",
			"H.",
			">Z",
	};

	public CharSequence showName(Name citation) {
		if(citation == null) return null;

		if(citation.getName() != null) {
			String formatted = "<i>" + citation.getName() + "</i>";
			// It's too complicated trying to reconstruct a full name from the data we have from ipni flat,
			// so we just look for the rank part of a name string and de-italicise it
			for(String rank : ranks) {
				if(formatted.contains(rank)) {
					formatted = formatted.replace(rank + " ", "</i> " + rank + " <i>");
					formatted = formatted.replace("<i></i>", "");
					break;
				}
			}

			return formatted;
		} else {
			return "<i>" + citation.getFamily() + "</i>";
		}
	}

	public CharSequence fullName(Name citation) {
		if(citation == null) return null;

		StringBuilder name = new StringBuilder();
		name.append(showName(citation));

		if(citation.getAuthors() != null) {
			name.append(" ");
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

			if(citation.getNameStatusType() != null || citation.getNameStatusBotCode() != null) {
				if(name.charAt(name.length() - 1) == '.') {
					name.setCharAt(name.length() - 1, ',');
				} else {
					name.append(",");
				}

				if(citation.getNameStatusType() != null) {
					name.append(" ");
					name.append(citation.getNameStatusType());
				}

				if(citation.getNameStatusBotCode() != null) {
					name.append(" ");
					name.append(citation.getNameStatusBotCode());
				}
			}

			if(name.charAt(name.length() - 1) != '.' && name.lastIndexOf("</h2>") != name.length() - 5) {
				name.append(".");
			}

			name.append("</small>");
		}

		return new Handlebars.SafeString(name);
	}

	public CharSequence linkedFullName(Name citation) {
		if(citation == null) return null;
		StringBuilder name = new StringBuilder();

		name.append("<h2>");
		name.append(showName(citation));

		if(citation.getAuthors() != null) {
			AuthorHelper authorHelper = new AuthorHelper();
			name.append(" ");
			name.append(authorHelper.linkAuthor(citation));
		}

		name.append("</h2>");

		if(hasReference(citation)) {
			name.append(", ");
			if(citation.getPublication() != null) {
				if(citation.getPublicationId() != null) {
					name.append("<a href=\"/p/");
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
		}

		if(citation.getNameStatusType() != null || citation.getNameStatusBotCode() != null) {
			if(name.charAt(name.length() - 1) == '.') {
				name.setCharAt(name.length() - 1, ',');
			} else {
				name.append(",");
			}

			if(citation.getNameStatusType() != null) {
				name.append(" ");
				name.append(citation.getNameStatusType());
			}

			if(citation.getNameStatusBotCode() != null) {
				name.append(" ");
				name.append(citation.getNameStatusBotCode());
			}
		}

		if(name.charAt(name.length() - 1) != '.' && name.lastIndexOf("</h2>") != name.length() - 5) {
			name.append(".");
		}

		return new Handlebars.SafeString(name);
	}

	private boolean hasReference(Name citation) {
		return !(citation.getPublication() == null
				&& citation.getReferenceCollation() == null
				&& citation.getPublicationYear() == null);
	}
}
