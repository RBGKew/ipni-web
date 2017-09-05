package org.ipni.view;

import java.util.List;
import java.util.stream.Collectors;

import org.ipni.model.NameAuthor;

import com.github.jknack.handlebars.Handlebars;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class AuthorHelper {

	private String link(NameAuthor author) {
		if(author.getId() != null) {
			return "<a href=\"/" + author.getFullyQualifiedId() + "\" class=\"author-link\">" + author.getName() + "</a>";
		} else {
			return author.getName();
		}
	}

	private String authorsFor(List<NameAuthor> team, String type) {
		return team.stream()
				.filter(author -> author.getType().equals(type))
				.sorted((NameAuthor a1, NameAuthor a2) -> Integer.compare(a1.getOrder(), a2.getOrder()))
				.map(author -> link(author))
				.collect(Collectors.joining(" & "));
	}

	private String authorStr(List<NameAuthor> team, String type) {
		String aut = authorsFor(team, type);
		String autEx = authorsFor(team, type + "Ex");
		String autIn = authorsFor(team, type + "In");

		StringBuilder str = new StringBuilder(aut);

		if(!autEx.isEmpty()) {
			str.append(" ex ");
			str.append(autEx);
		}

		if(!autIn.isEmpty()) {
			str.append(" in ");
			str.append(autIn);
		}

		return str.toString();
	}

	public CharSequence linkAuthor(List<NameAuthor> team) {
		String basonymStr = authorStr(team, "bas");
		if(!basonymStr.isEmpty()) {
			basonymStr = "(" + basonymStr + ") ";
		}

		return new Handlebars.SafeString(basonymStr + authorStr(team, "aut"));
	}
}
