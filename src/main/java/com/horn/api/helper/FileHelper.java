package com.horn.api.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.apache.tika.Tika;

import com.horn.api.model.util.Dimension;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileHelper {
	private final static Tika tika = new Tika();

	public static InputStream fileToInputStream(String filePath) throws IOException {
		File file = new File(filePath);

		FileInputStream fileStream = new FileInputStream(file);
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];

		for (int readNum; (readNum = fileStream.read(buffer)) != -1;) {
			byteOutputStream.write(buffer, 0, readNum);
		}

		fileStream.close();
		byte[] fileBytes = byteOutputStream.toByteArray();
		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(fileBytes);

		return (InputStream) byteInputStream;
	}

	/**
	 * Gets image dimensions for given file
	 * 
	 * @param imgFile image file
	 * @return dimensions of image
	 * @throws IOException if the file is not a known image
	 */
	public static Dimension getImageDimension(File imgFile) throws IOException {
		int pos = imgFile.getName().lastIndexOf(".");
		if (pos == -1)
			throw new IOException("No extension for file: " + imgFile.getAbsolutePath());
		String suffix = imgFile.getName().substring(pos + 1);
		Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
		while (iter.hasNext()) {
			ImageReader reader = iter.next();
			try {
				ImageInputStream stream = new FileImageInputStream(imgFile);
				reader.setInput(stream);
				int width = reader.getWidth(reader.getMinIndex());
				int height = reader.getHeight(reader.getMinIndex());
				return new Dimension(width, height);
			} catch (IOException e) {
				log.warn("Error reading: " + imgFile.getAbsolutePath(), e);
			} finally {
				reader.dispose();
			}
		}

		throw new IOException("Not a known image file: " + imgFile.getAbsolutePath());
	}

	public static String getMediaMimeType(String file) {
		try (FileInputStream fis = new FileInputStream(file)) {
			return tika.detect(fis, file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isImage(String file) {
		String mime = getMediaMimeType(file);
		return mime.contains("/") && mime.split("/")[0].equalsIgnoreCase("image");

	}
}
