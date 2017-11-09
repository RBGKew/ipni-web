package org.ipni.view;

import static org.junit.Assert.assertEquals;

import org.ipni.model.Name;
import org.junit.Test;

public class NameHelperTest {

	private static final NameHelper helper = new NameHelper();

	@Test
	public void linkedFullNameWithNoAuthorOrReference() {
		Name name = Name.builder().name("Quercus").build();
		String expected = "<h2><i>Quercus</i></h2>";
		String actual = helper.linkedFullName(name).toString();

		assertEquals(expected, actual);
	}
}
