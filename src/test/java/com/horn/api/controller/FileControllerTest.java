package com.horn.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.horn.api.service.FileService;

@WebMvcTest(controllers = FileController.class)
public class FileControllerTest {
	@Autowired
	MockMvc mock;
	
	@MockBean
	FileService fileService;
	
	@Test
	public void testGetFiles() throws Exception {
		mock.perform(get("/files")).andExpect(status().isOk());
	}
}
