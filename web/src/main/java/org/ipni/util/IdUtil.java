package org.ipni.util;

public class IdUtil {

	enum Type { Name, Author, Publication }

	public static final String urnPrefix = "urn:lsid:ipni.org";

	public static String fqName(String id) {
		return fq(id, Type.Name);
	}

	public static String fqAuthor(String id) {
		return fq(id, Type.Author);
	}

	public static String fqPublication(String id) {
		return fq(id, Type.Publication);
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
