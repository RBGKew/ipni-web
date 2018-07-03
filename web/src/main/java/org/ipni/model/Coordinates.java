package org.ipni.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class Coordinates {
	private String latitudeDegrees;
	private String latitudeMinutes;
	private String latitudeSeconds;
	private String longitudeDegrees;
	private String longitudeMinutes;
	private String longitudeSeconds;
	private String northOrSouth;
	private String eastOrWest;

	public boolean isValid() {
		return notAllZero() && notAllEmpty();
	}

	public String getFormattedLatitude() {
		StringBuilder sb = new StringBuilder();
		if(isValid()) {
			if(notEmpty(latitudeDegrees)) {
				sb.append(latitudeDegrees);
				sb.append("° ");
			}
			if(notEmpty(latitudeMinutes)) {
				sb.append(latitudeMinutes);
				sb.append("' ");
			}
			if(notEmpty(latitudeSeconds)) {
				sb.append(latitudeSeconds);
				sb.append("\" ");
			}
			sb.append(northOrSouth);
		}

		if(sb.length() != 0) {
			return sb.toString();
		} else {
			return null;
		}
	}

	public String getFormattedLongitude() {
		StringBuilder sb = new StringBuilder();
		if(isValid()) {
			if(notEmpty(longitudeDegrees)) {
				sb.append(longitudeDegrees);
				sb.append("° ");
			}
			if(notEmpty(longitudeMinutes)) {
				sb.append(longitudeMinutes);
				sb.append("' ");
			}
			if(notEmpty(longitudeSeconds)) {
				sb.append(longitudeSeconds);
				sb.append("\" ");
			}
			sb.append(eastOrWest);
		}

		if(sb.length() != 0) {
			return sb.toString();
		} else {
			return null;
		}
	}

	private boolean notAllZero() {
		return !("0".equals(latitudeDegrees)
				&& "0".equals(latitudeMinutes)
				&& "0".equals(latitudeSeconds)
				&& "0".equals(longitudeDegrees)
				&& "0".equals(longitudeMinutes)
				&& "0".equals(longitudeSeconds));
	}

	private boolean notAllEmpty() {
		return !(isEmpty(latitudeDegrees)
				&& isEmpty(latitudeMinutes)
				&& isEmpty(latitudeSeconds)
				&& isEmpty(longitudeDegrees)
				&& isEmpty(longitudeMinutes)
				&& isEmpty(longitudeSeconds));
	}


	private boolean isEmpty(String val) {
		return !notEmpty(val);
	}

	private boolean notEmpty(String val) {
		return val != null && !val.isEmpty();
	}
}
