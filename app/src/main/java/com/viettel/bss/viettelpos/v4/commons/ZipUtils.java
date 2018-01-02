package com.viettel.bss.viettelpos.v4.commons;

import android.annotation.SuppressLint;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtils {
	public static void unzip(String strArchive, String strPath)
			throws IOException {
		ZipInputStream zip = null;
		String fileName = null;
		try {
			File archive = new File(strArchive);
			File path = new File(strPath);
			if (!path.exists()) {
				path.mkdirs();
			}
			zip = new ZipInputStream(new FileInputStream(archive));
			ZipEntry zipEntry;
			while ((zipEntry = zip.getNextEntry()) != null) {
				fileName = zipEntry.getName();
				final File outputFile = new File(path, fileName);
				writeToStream(new BufferedInputStream(zip),
						new FileOutputStream(outputFile), false);
				zip.closeEntry();
			}
			zip.close();
			zip = null;
		} finally {
			if (zip != null) {
				try {
					zip.close();
				} catch (Exception ignored) {
				}
			}
		}
	}

	private static void writeToStream(InputStream in, OutputStream out,
                                      boolean closeOnExit) throws IOException {
		byte[] bytes = new byte[2048];
		for (int c = in.read(bytes); c != -1; c = in.read(bytes)) {
			out.write(bytes, 0, c);
		}
		if (closeOnExit) {
			in.close();
			out.close();
		}
	}

	public static String writeToString(InputStream stream)
			throws java.io.IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				stream, "utf-8"));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}

	@SuppressLint("Assert")
    public static void join(String fname, String desName, Integer startIndex,
                            Integer endIndex) throws Exception {
		File ofile = new File(desName);
		if (!ofile.exists()) {
			ofile.createNewFile();
		}

		FileOutputStream fos;
		FileInputStream fis;
		byte[] fileBytes;
		int bytesRead = 0;
		try {
			fos = new FileOutputStream(ofile, true);
			for (int i = startIndex; i <= endIndex; i++) {
				String newName = fname + ".part" + Integer.toString(i);
				File file = new File(newName);
				if (file != null && file.exists() && file.length() > 0) {
					fis = new FileInputStream(file);
					fileBytes = new byte[(int) file.length()];
					bytesRead = fis.read(fileBytes, 0, (int) file.length());
					assert (bytesRead == fileBytes.length);
					assert (bytesRead == (int) file.length());
					fos.write(fileBytes);
					fos.flush();
					fileBytes = null;
					fis.close();
					fis = null;
				}
			}
			fos.close();
			fos = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<File> unzip(File zipFile, File targetDirectory) {
		List<File> lstOutFile = new ArrayList<>();
		
		ZipInputStream zis = null;
		ZipEntry ze = null;
		try {
			zis = new ZipInputStream(new BufferedInputStream(
					new FileInputStream(zipFile)));
			int count;
			byte[] buffer = new byte[8192];
			while ((ze = zis.getNextEntry()) != null) {
				File file = new File(targetDirectory, ze.getName());
				File dir = ze.isDirectory() ? file : file.getParentFile();
				if (!dir.isDirectory() && !dir.mkdirs())
					throw new FileNotFoundException(
							"Failed to ensure directory: "
									+ dir.getAbsolutePath());
				if (ze.isDirectory())
					continue;
                try (FileOutputStream fout = new FileOutputStream(file)) {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);

                    lstOutFile.add(file);
                }
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				zis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lstOutFile;
	}

	
	public static void unzipWithFolder(File zipFile, File targetDirectory)
			throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(
                new FileInputStream(zipFile)))) {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {

                String pathFile = targetDirectory.getPath() + "/" + ze.getName();
                File file = new File(pathFile);
//				File file = new File(targetDirectory, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException(
                            "Failed to ensure directory: "
                                    + dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                try (FileOutputStream fout = new FileOutputStream(file)) {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                }
            }
        }
	}  
	
	private static List<File> unzipListFile(File archive, File desUnzip)
			throws IOException {
		ZipInputStream zip = null;
		String fileName = null;
		List<File> listFile = new ArrayList<>();
		
		try {
			if (!desUnzip.exists()) {
				desUnzip.mkdirs();
			}
			zip = new ZipInputStream(new FileInputStream(archive));
			ZipEntry zipEntry;
			while ((zipEntry = zip.getNextEntry()) != null) {
				fileName = zipEntry.getName();
				final File outputFile = new File(desUnzip, fileName);
				writeToStream(new BufferedInputStream(zip),
						new FileOutputStream(outputFile), false);
				zip.closeEntry();
				
				listFile.add(outputFile);
			}
			zip.close();
			zip = null;
		} finally {
			if (zip != null) {
				try {
					zip.close();
				} catch (Exception ignored) {
				}
			}
		}
		return listFile;
	}
	
	
}
