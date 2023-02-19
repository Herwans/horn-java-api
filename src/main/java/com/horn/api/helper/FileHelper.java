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

		return byteInputStream;
	}

	/**
	 * Gets image dimensions for given file
	 * 
	 * @param imageFile image file
	 * @return dimensions of image
	 * @throws IOException if the file is not a known image
	 */
	public static Dimension getImageDimension(File imageFile) throws IOException {
		int pos = imageFile.getName().lastIndexOf(".");
		if (pos == -1)
			throw new IOException("No extension for file: " + imageFile.getAbsolutePath());
		String suffix = imageFile.getName().substring(pos + 1);
		Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
		while (iter.hasNext()) {
			ImageReader imageReader = iter.next();
			try {
				ImageInputStream imageInputStream = new FileImageInputStream(imageFile);
				imageReader.setInput(imageInputStream);
				int width = imageReader.getWidth(imageReader.getMinIndex());
				int height = imageReader.getHeight(imageReader.getMinIndex());
				return new Dimension(width, height);
			} catch (IOException e) {
				log.warn("Error reading: " + imageFile.getAbsolutePath(), e);
			} finally {
				imageReader.dispose();
			}
		}

		throw new IOException("Not an image");
	}

	/**
	 * Gets the mime type of the file
	 * @param file
	 * @return
	 */
	public static String getMediaMimeType(String file) {
		try (FileInputStream fis = new FileInputStream(file)) {
			return tika.detect(fis, file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns if the file is an image based on the file's mime type
	 * @param file
	 * @return true if the mime type start with "image"
	 */
	public static boolean isImage(String file) {
		String mime = getMediaMimeType(file);
		return mime.contains("/") && mime.split("/")[0].equalsIgnoreCase("image");
	}
	
	public static boolean isVideo(String file) {
		String mime = getMediaMimeType(file);
		return mime.contains("/") && mime.split("/")[0].equalsIgnoreCase("video");

	}
}
