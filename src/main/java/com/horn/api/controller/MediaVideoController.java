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
import com.horn.api.model.MediaVideo;
import com.horn.api.service.MediaVideoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/videos")
@Slf4j
public class MediaVideoController {
	@Autowired
	private MediaVideoService mediaVideoService;
	
	@GetMapping
	public ResponseEntity<Iterable<MediaVideo>> getVideos() {
		return ResponseEntity.ok(mediaVideoService.getMedia());
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<InputStreamResource> getVideo(@PathVariable("id") final long id) {
		String file = mediaVideoService.getMedium(id).get().getFile().getPath();
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
