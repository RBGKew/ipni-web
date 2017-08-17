package org.ipni.view;

import java.io.IOException;

import org.junit.Test;

import com.github.jknack.handlebars.Handlebars;

public class BHLHelperTest extends AbstractHelperTest {

	@Override
	protected Handlebars newHandlebars() {
		Handlebars handlebars = super.newHandlebars();
		handlebars.registerHelpers(new BHLHelper());
		return handlebars;
	}

	@Test
	public void findBHLMarkers() throws IOException {
		shouldCompileTo("{{#hasBhlMarkers}}blah{{/hasBhlMarkers}}", "[BHL_page_id:690]", "blah");
		shouldCompileTo("{{#hasBhlMarkers}}blah{{/hasBhlMarkers}}", "blah blah [BHL_page_id:690] blah", "blah");
		shouldCompileTo("{{#hasBhlMarkers}}blah{{/hasBhlMarkers}}", "blah blah", "");
	}

	@Test
	public void generatePageLinks() throws IOException {
		shouldCompileTo("{{#withBhlPage}}it's a {{link}}{{/withBhlPage}}", "[BHL_page_id:213]", "it's a http://www.biodiversitylibrary.org/openurl?pid=page:213");
	}

	@Test
	public void generateTitleLinks() throws IOException {
		shouldCompileTo("{{#withBhlTitle}}it's a {{link}}{{/withBhlTitle}}", "[BHL_title_id:213]", "it's a http://www.biodiversitylibrary.org/openurl?pid=title:213");
	}

	@Test
	public void stripBHLMarkers() throws IOException {
		shouldCompileTo("{{stripBhlMarkers}}", "blah de blah [BHL_title_id:321]blah[BHL_title_id:123]. And[BHL_page_id:876] now", "blah de blah blah. And now");
	}
}
