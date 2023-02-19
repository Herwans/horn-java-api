package com.horn.api.controller;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.horn.api.helper.FileHelper;
import com.horn.api.model.File;
import com.horn.api.service.FileService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/files")
@Slf4j
public class FileController {
	@Autowired
	private FileService fileService;

	@GetMapping
	public Iterable<File> getExtensions() {
		return fileService.getFiles();
	}

	@PostMapping
	public File addFile(@RequestBody String filepath) {
		return fileService.saveFile(filepath);
	}

	@GetMapping(value = "/videos/{id}")
	public ResponseEntity<InputStreamResource> getVideo(@PathVariable("id") final long id) {
		String file = fileService.getFile(id).get().getPath();
		try {
			InputStream input = FileHelper.fileToInputStream(file);

			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType("video/" + FilenameUtils.getExtension(file)))
					.body(new InputStreamResource(input));
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}
}
