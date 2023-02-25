package com.horn.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horn.api.model.Directory;
import com.horn.api.model.Extension;
import com.horn.api.model.File;
import com.horn.api.repository.FileRepository;

import lombok.Data;

@Data
@Service
public class FileService {
	@Autowired
	private FileRepository repository;

	@Autowired
	private DirectoryService directoryService;
	
	@Autowired
	private ExtensionService extensionService;

	public Optional<File> getFile(final Long id) {
		return repository.findById(id);
	}

	public Iterable<File> getFiles() {
		return repository.findAll();
	}
	
	public Iterable<File> getUnsortedFiles() {
		// TODO Implement it
		return repository.findAll();
	}

	public void deleteFile(final Long id) {
		repository.deleteById(id);
	}

	public File saveFile(String fileStr) {
		return repository.save(prepare(fileStr));
	}
	
	public void saveAllFiles(List<String> files) {
		List<File> result = new ArrayList<File>();
		for(String file: files) {
			result.add(prepare(file));
		}

		repository.saveAll(result);
	}
	
	private File prepare(String path) {
		File file = new File();
		Directory dir = new Directory();
		Extension ext = new Extension();
		ext.setName(FilenameUtils.getExtension(path));
		file.setName(FilenameUtils.getBaseName(path));
		try {
			file.setSize(Files.size(Paths.get(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		dir.setName(FilenameUtils.getFullPathNoEndSeparator(path));
		dir = directoryService.saveOrGetDirectory(dir);
		ext = extensionService.saveOrGetExtension(ext);
		
		
		file.setDirectory(dir);
		file.setExtension(ext);
		
		System.out.println(file);
		
		return file;
	}
}
