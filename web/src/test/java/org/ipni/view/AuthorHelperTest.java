package org.ipni.view;

import java.io.IOException;
import java.util.List;

import org.ipni.model.Name;
import org.ipni.model.NameAuthor;
import org.junit.Test;

import com.github.jknack.handlebars.Handlebars;
import com.google.common.collect.ImmutableList;

public class AuthorHelperTest extends AbstractHelperTest {

	@Override
	protected Handlebars newHandlebars() {
		Handlebars handlebars = super.newHandlebars();
		handlebars.registerHelpers(new AuthorHelper());
		return handlebars;
	}

	List<NameAuthor> team = ImmutableList.<NameAuthor>of(
			NameAuthor.builder().id("14556-1").name("Z.R.Wang").type("bas").order(5).build(),
			NameAuthor.builder().id("13738-1").name("W.M.Chu").type("aut").order(1).build(),
			NameAuthor.builder().id("14556-1").name("Z.R.Wang").type("aut").order(2).build(),
			NameAuthor.builder().id("14980-1").name("P.S.Wang").type("autEx").order(3).build(),
			NameAuthor.builder().id(null).name("X.Y.Wang").type("autEx").order(4).build());

	@Test
	public void buildLinkedAuthorString() throws IOException {
		Name name = Name.builder().authorTeam(team).build();
		String expected = "(<a href=\"/urn:lsid:ipni.org:authors:14556-1\" class=\"author-link\">Z.R.Wang</a>) "
				+ "<a href=\"/urn:lsid:ipni.org:authors:13738-1\" class=\"author-link\">W.M.Chu</a> & <a href=\"/urn:lsid:ipni.org:authors:14556-1\" class=\"author-link\">Z.R.Wang</a> "
				+ "ex <a href=\"/urn:lsid:ipni.org:authors:14980-1\" class=\"author-link\">P.S.Wang</a> & X.Y.Wang";

		shouldCompileTo("{{linkAuthor}}", name, expected);
	}

	@Test
	public void usePrebuiltAuthorWhenAuthorTeamIsMissing() throws IOException {
		Name name = Name.builder().authors("Z.R.Wang & P.S.Wang").build();
		String expected = "Z.R.Wang & P.S.Wang";

		shouldCompileTo("{{linkAuthor}}", name, expected);
	}
}
