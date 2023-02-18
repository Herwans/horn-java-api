package com.horn.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Service
@Slf4j
public class ExplorerService {
	@Autowired
	private FileService fileService;
	
	public List<Path> getAllFilesAsPath(String startingPoint) throws IOException {
		List<Path> result = new ArrayList<Path>();
		Path rootPath = Path.of(startingPoint);

		Iterator<Path> itr = Files.walk(rootPath).iterator();
		while(true) {
		    try {
		        if(itr.hasNext()) {
		        	Path current = itr.next();
		        	if(Files.isRegularFile(current)) {
						result.add(current);
		        	}
		        	
		        } else {
		            break;
		        }
		    }catch(Exception e) {
		        System.out.println(e.getLocalizedMessage());
		    }
		}
		return result;
	}
	
	public void walkAndSave(String startingPoint) throws IOException {
		//List<String> result = new ArrayList<String>();
		Path rootPath = Path.of(startingPoint);
		Iterator<Path> itr = Files.walk(rootPath).iterator();
		while(true) {
		    try {
		        if(itr.hasNext()) {
		        	Path current = itr.next();
		        	if(Files.isRegularFile(current)) {
		        		fileService.saveFile(current.toString());
						//result.add(current.toString());	
		        	}
		        } else {
		            break;
		        }
		    }catch(Exception e) {
		    	log.error(e.getMessage());
		    }
		}
		//fileService.saveAllFiles(result);
	}
}
