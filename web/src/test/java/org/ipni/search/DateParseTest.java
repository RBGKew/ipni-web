package org.ipni.search;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

public class DateParseTest {

	@Test
	public void testDateParse() {
		LocalDate year = LocalDate.parse("2012-01-01", DateTimeFormatter.ISO_DATE);
		System.out.println(year.atStartOfDay(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT));
	}
}
