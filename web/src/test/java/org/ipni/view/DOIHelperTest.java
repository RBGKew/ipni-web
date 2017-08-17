package org.ipni.view;

import java.io.IOException;

import org.junit.Test;

import com.github.jknack.handlebars.Handlebars;

public class DOIHelperTest extends AbstractHelperTest {

	@Override
	protected Handlebars newHandlebars() {
		Handlebars handlebars = super.newHandlebars();
		handlebars.registerHelpers(new DOIHelper());
		return handlebars;
	}

	@Test
	public void linkifyDOIs() throws IOException {
		shouldCompileTo("{{linkDoi}}",
				"doi:10.3100/hpib.v21iss1.2016.n6 [english]",
				"<a href=\"https://dx.doi.org/10.3100/hpib.v21iss1.2016.n6\" target=\"_blank\">doi:10.3100/hpib.v21iss1.2016.n6</a> [english]");
	}
}
