package com.horn.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.horn.api.model.Extension;
import com.horn.api.service.ExtensionService;

@RestController
@RequestMapping("/extensions")
public class ExtensionController {
	@Autowired
	private ExtensionService service;
	
	@GetMapping
	public Iterable<Extension> getExtensions() {
		return service.getExtensions();
	}
}
