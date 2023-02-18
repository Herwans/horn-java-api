package com.horn.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horn.api.model.Directory;
import com.horn.api.repository.DirectoryRepository;

import lombok.Data;

@Data
@Service
public class DirectoryService {
	@Autowired
	private DirectoryRepository repository;
	
	public Optional<Directory> getDirectory(final Long id) {
		return repository.findById(id);
	}
	
	public Iterable<Directory> getDirectories() {
		return repository.findAll();
	}
	
	public void deleteDirectory(final Long id) {
		repository.deleteById(id);
	}
	
	public Directory saveDirectory(Directory directory) {
		return repository.save(directory);
	}
	
	public Directory saveOrGetDirectory(Directory directory) {
		try {
			return saveDirectory(directory);
		} catch (Exception e) {
			return repository.getByName(directory.getName());
		}
	}
}
