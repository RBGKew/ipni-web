package org.ipni.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CoordinatesTest {

	@Test
	public void testAllZeroNotValid() {
		Coordinates coordinates = Coordinates.builder()
				.latitudeDegrees("0").latitudeMinutes("0").latitudeSeconds("0")
				.longitudeDegrees("0").longitudeMinutes("0").longitudeSeconds("0")
				.build();

		assertFalse(coordinates.isValid());
	}

	@Test
	public void testValidCoordinates() {
		Coordinates equitorial = Coordinates.builder()
				.longitudeDegrees("3").longitudeMinutes("12").longitudeSeconds("0")
				.build();
		Coordinates coordinate = Coordinates.builder()
				.latitudeDegrees("21").latitudeMinutes("9").latitudeSeconds("1")
				.longitudeDegrees("3").longitudeMinutes("12").longitudeSeconds("0")
				.build();

		assertTrue(equitorial.isValid());
		assertTrue(coordinate.isValid());
	}

	@Test
	public void testLatitudeFormatting() {
		Coordinates coordinate = Coordinates.builder()
				.latitudeDegrees("3").latitudeMinutes("8").latitudeSeconds("2").northOrSouth("N")
				.build();
		assertEquals("3° 8' 2\" N", coordinate.getFormattedLatitude());
	}

	@Test
	public void testLongitudeFormatting() {
		Coordinates coordinate = Coordinates.builder()
				.longitudeDegrees("3").longitudeMinutes("12").longitudeSeconds("0").eastOrWest("E")
				.build();
		assertEquals("3° 12' 0\" E", coordinate.getFormattedLongitude());
	}
}
