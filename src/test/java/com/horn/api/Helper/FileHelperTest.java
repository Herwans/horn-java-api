package com.horn.api.Helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.horn.api.helper.FileHelper;
import com.horn.api.model.util.Dimension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { FileHelper.class })
@EnableConfigurationProperties
public class FileHelperTest {
	@BeforeAll
	public static void setup() {
		int width = 1;
		int height = 1;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();
        try {
            File file = new File("test-image.png");
			ImageIO.write(bufferedImage, "png", file);
	        file = new File("test-image.jpg");
	        ImageIO.write(bufferedImage, "jpg", file);
	        file = new File("test-text.txt");
	        file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterAll
	public static void clean() {
		try {
			Files.deleteIfExists(Paths.get("test-image.jpg"));
			Files.deleteIfExists(Paths.get("test-image.png"));
			Files.deleteIfExists(Paths.get("test-text.txt"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getDimensionSuccessfulTest() {
		Dimension d = new Dimension(1,1);
		try {
			Dimension r = FileHelper.getImageDimension(new File("test-image.png"));
			assertEquals(d.getHeight(), r.getHeight());
			assertEquals(d.getWidth(), r.getWidth());
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void getDimensionFailedTest() {
		try {
			Dimension r = FileHelper.getImageDimension(new File("test-text.txt"));
			fail();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getMediaMimeTypeSuccessfulTest() {
		assertEquals("image/png", FileHelper.getMediaMimeType("test-image.png"));
		assertEquals("image/jpeg", FileHelper.getMediaMimeType("test-image.jpg"));
		assertEquals("text/plain", FileHelper.getMediaMimeType("test-text.txt"));
	}
	
	@Test
	public void isImageSuccessfulTest() {
		assertTrue(FileHelper.isImage("test-image.png"));
		assertTrue(FileHelper.isImage("test-image.jpg"));
	}
	
	@Test
	public void isNotImageSuccessfulTest() {
		assertFalse(FileHelper.isImage("test-text.txt"));
	}
	
	
}
