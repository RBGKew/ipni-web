package org.ipni.util;

/**
 * IPNI data is messy in various ways. While the data is being cleaned, we can tidy up
 * the display for large classes of messiness
 */
public class CleanUtil {

	/**
	 * Empty Lat/Lon values are sometimes represented with all 0's. 
	 */
	public static String zeroToNull(String str) {
		if(str == null) return null;
		return str.equals("0") ? null : str;
	}

	/**
	 * The originalRemarks field has notes about taxonomic synonyms that begin with =.
	 * We don't ever want to show these.
	 */
	public static String equalsPrefixedToNull(String str) {
		if(str == null) return null;
		return str.trim().startsWith("=") ? null : str;
	}

	public static String stripBrackets(String str) {
		if(str == null) return null;
		return str.replaceAll("\\[|\\]", "");
	}

	public static String stripThingsInBrackets(String str) {
		if(str == null) return null;
		return str.replaceAll("\\[.*\\]", "");
	}
}
