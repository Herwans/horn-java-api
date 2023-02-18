package com.horn.api.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileHelper {
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
}
