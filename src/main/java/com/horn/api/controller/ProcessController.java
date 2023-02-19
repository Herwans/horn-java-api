package com.horn.api.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.horn.api.service.ExplorerService;

@RestController
@RequestMapping("/process")
@EnableAsync
public class ProcessController {
	
	@Autowired
	private ExplorerService explorerService;
	
	@Async
	@PostMapping("/walk")
	public void walkFile(@RequestBody String filepath) {
		try {
			explorerService.walkAndSave(filepath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Async
	@PostMapping("/sort")
	public void sortFiles() {
		explorerService.sortFiles();
	}
}
