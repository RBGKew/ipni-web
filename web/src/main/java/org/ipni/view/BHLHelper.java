package org.ipni.view;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.ipni.model.BHLLink;
import org.ipni.model.Name;
import org.ipni.model.Publication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BHLHelper {

	private static final Pattern TITLE = Pattern.compile("\\[BHL_title_id:(\\d+)\\]");
	private static final Pattern PAGE = Pattern.compile("\\[BHL_page_id:(\\d+)\\]");
	private static final Logger log = LoggerFactory.getLogger(BHLHelper.class);

	public static List<String> buildPublicationPageLinks(List<String> pageIds) {
		if(pageIds == null) return null;

		return pageIds.stream()
				.map(id -> new BHLLink().withPageId(id).build())
				.collect(Collectors.toList());
	}

	public static List<String> buildPublicationTitleLinks(List<String> titleIds) {
		if(titleIds == null) return null;

		return titleIds.stream()
				.map(id -> new BHLLink().withTitleId(id).build())
				.collect(Collectors.toList());
	}

	public static String buildNameLink(Name name, Publication publication) {
		if(name == null) {
			log.debug("bhlLink: name is null, returning null");
			return null;
		}

		if(name.getPageAsText() != null) {
			return new BHLLink().withPageId(name.getPageAsText()).build();
		}

		if(publication == null) {
			log.debug("bhlLink: has no linked publication, returning null");
			return null;
		}

		if(!name.getCollation().isParsed()) {
			log.debug("bhlLink: collation could not be parsed, no point trying to build link");
			return null;
		}

		if(publication.getBhlTitleIds() != null && !publication.getBhlTitleIds().isEmpty()) {
			BHLLink link = new BHLLink()
					.withCollation(name.getCollation())
					.withYear(name.getPublicationYear());

			if(publication.getBhlTitleIds().size() > 1) {
				log.debug("bhlLink: publication has more than one bhl title link, returning null");
				return null;
			}

			if(publication.getBhlTitleIds().size() == 1) {
				link.withTitleId(publication.getBhlTitleIds().get(0));
			}

			return link.build();
		}

		return null;
	}

	public static List<String> extractPageIds(String text) {
		return extractId(text, PAGE);
	}

	public static List<String> extractTitleIds(String text) {
		return extractId(text, TITLE);
	}

	public static String stripBhlMarkers(String text) {
		if(text == null) {
			return null;
		}

		text = TITLE.matcher(text).replaceAll("");
		text = PAGE.matcher(text).replaceAll("");

		text = text.trim();

		return text.isEmpty() ? null : text;
	}

	private static List<String> extractId(String text, Pattern pattern) {
		if(text == null) return null;

		Matcher matcher = pattern.matcher(text);

		List<String> links = new ArrayList<>();

		while(matcher.find()) {
			links.add(matcher.group(1));
		}

		return links.isEmpty() ? null : links;
	}
}
