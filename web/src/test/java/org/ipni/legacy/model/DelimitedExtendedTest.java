package org.ipni.legacy.model;

import org.ipni.model.Name;
import org.junit.Test;

import com.google.common.base.Joiner;

public class DelimitedExtendedTest {
	@Test
	public void delimitedClassicTest() {
		Name name = Name.builder()
				.id("77100052-1")
				.version("1.1")
				.family("Orchidaceae")
				.infrafamily("Infrafam")
				.hybridGenus(false)
				.genus("Stolzia")
				.infragenus("Infragen")
				.hybrid(false)
				.species("repens")
				.speciesAuthor("(Rolfe) Summerh.")
				.infraspecies("cleistogama")
				.rank("var.")
				.authors("Stévart, Droissart & M.Simo")
				.publishingAuthor("Stévart, Droissart & M.Simo")
				.name("Stolzia repens var. cleistogama")
				.reference("Adansonia 31(1): 30 (-34; fig. 3, map). 2009 [26 Jun 2009] ")
				.publication("Adansonia")
				.referenceCollation("31(1): 30 (-34; fig. 3, map)")
				.remarks("remark")
				.distribution("Cameroon (West-Central Tropical Africa, Africa)")
				.citationType("tax. nov.")
				.bibliographicReference("reference")
				.collectionDate1("1 April 2018")
				.collectionDate2("2 April 2018")
				.build();
		DelimitedExtended dc = new DelimitedExtended(name);
		System.out.println(Joiner.on(",").join(DelimitedExtended.fields));
		System.out.print(dc.toDelimited());
	}
}
