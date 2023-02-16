package com.horn.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.horn.api.model.Extension;
import com.horn.api.repository.ExtensionRepository;

import lombok.Data;

@Data
@Service
public class ExtensionService {
	@Autowired
	private ExtensionRepository repository;
	
	public Optional<Extension> getExtension(final Long id) {
		return repository.findById(id);
	}
	
	public Iterable<Extension> getExtensions() {
		return repository.findAll();
	}
	
	public void deleteExtension(final Long id) {
		repository.deleteById(id);
	}
	
	public Extension saveExtension(Extension extension) {
		extension.setName(extension.getName().trim().toLowerCase());
		Extension savedExtension;
		try {
			savedExtension = repository.save(extension);
		} catch (DataIntegrityViolationException e) {
			savedExtension = repository.findByName(extension.getName());
		}
		return savedExtension;
	}
	
	public Extension saveOrGetExtension(Extension extension) {
		try {
			return saveExtension(extension);
		} catch (Exception e) {
			return repository.findByName(extension.getName());
		}
	}
}
