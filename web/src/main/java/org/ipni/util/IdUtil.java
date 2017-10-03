package org.ipni.util;

public class IdUtil {

	enum Type { Names, Authors, Publications }

	public static final String urnPrefix = "urn:lsid:ipni.org";

	public static String fqAuthor(String id) {
		return fq(id, Type.Authors);
	}

	public static String fqName(String id) {
		return fq(id, Type.Names);
	}

	public static String fqPublication(String id) {
		return fq(id, Type.Publications);
	}

	public static boolean isAuthorId(String id) {
		return id.startsWith(fq("", Type.Authors));
	}

	public static boolean isNameId(String id) {
		return id.startsWith(fq("", Type.Names));
	}

	public static boolean isPublicationId(String id) {
		return id.startsWith(fq("", Type.Publications));
	}

	private static String fq(String id, Type type) {
		if(id.startsWith(urnPrefix)) {
			return id;
		}

		return String.format("%s:%s:%s", 
				urnPrefix,
				type.toString().toLowerCase(),
				id);
	}
}
