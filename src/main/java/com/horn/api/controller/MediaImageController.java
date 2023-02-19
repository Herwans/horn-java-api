package com.horn.api.controller;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.horn.api.helper.FileHelper;
import com.horn.api.service.MediaImageService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/images")
@Slf4j
public class MediaImageController {
	@Autowired
	private MediaImageService mediaImageService;
	
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<InputStreamResource> getImage(@PathVariable("id") final long id) {
		String file = mediaImageService.getMedium(id).get().getFile().getPath();
		MediaType contentType = new MediaType(MimeTypeUtils.parseMimeType(FileHelper.getMediaMimeType(file)));

		try {
			InputStream input = FileHelper.fileToInputStream(file);
			return ResponseEntity.ok().contentType(contentType).body(new InputStreamResource(input));
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}
}
