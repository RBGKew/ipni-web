package org.ipni.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipWriter {

	public static File createZipFile(File outputFile, List<File> files) throws FileNotFoundException, IOException {
		try (FileOutputStream output = new FileOutputStream(outputFile);
				ZipOutputStream zip = new ZipOutputStream(output)) {
			for(File file : files) {
				try(FileInputStream input = new FileInputStream(file)) {
					ZipEntry entry = new ZipEntry(file.getName());

					zip.putNextEntry(entry);

					byte[] bytes = new byte[1024];
					int length;
					while((length = input.read(bytes)) >= 0) {
						zip.write(bytes, 0, length);
					}

					zip.closeEntry();
				}
			}
		}

		return outputFile;
	}
}
