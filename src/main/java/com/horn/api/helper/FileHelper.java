package com.horn.api.helper;

import com.horn.api.model.util.Dimension;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.util.Iterator;

@Slf4j
public class FileHelper {
    private final static Tika tika = new Tika();

    public static byte[] fileToByte(String filePath) throws IOException {
        File file = new File(filePath);

        FileInputStream fileStream = new FileInputStream(file);
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        for (int readNum; (readNum = fileStream.read(buffer)) != -1; ) {
            byteOutputStream.write(buffer, 0, readNum);
        }

        fileStream.close();
        return byteOutputStream.toByteArray();
    }

    public static InputStream fileToInputStream(String filePath) throws IOException {
        return new ByteArrayInputStream(fileToByte(filePath));
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

    public static boolean isVideo(String file) {
        String mime = getMediaMimeType(file);
        return mime.contains("/") && mime.split("/")[0].equalsIgnoreCase("video");
    }

    public static Long videoLength(String path) throws IOException, TikaException, SAXException {
        InputStream input = new FileInputStream(path);
        ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata();
        Parser parser = new Mp3Parser();
        ParseContext parseCtx = new ParseContext();
        parser.parse(input, handler, metadata, parseCtx);
        input.close();
        return Long.parseLong(metadata.get("xmpDM:duration").split("\\.")[0]);
    }
}
