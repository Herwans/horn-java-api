package com.horn.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.horn.api.model.File;
import com.horn.api.service.FileService;

@RestController
@RequestMapping("/files")
public class FileController {
	@Autowired
	private FileService fileService;

	@GetMapping
	public Iterable<File> getFiles() {
		return fileService.getFiles();
	}

	@PostMapping
	public File addFile(@RequestBody String filepath) {
		return fileService.saveFile(filepath);
	}
}
