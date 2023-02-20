package com.horn.api.controller;

import com.horn.api.model.MediaFile;
import com.horn.api.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired
    private FileService fileService;

    @GetMapping
    public Iterable<MediaFile> getExtensions() {
        return fileService.getFiles();
    }

    @PostMapping
    public MediaFile addFile(@RequestBody String filepath) {
        return fileService.saveFile(filepath);
    }
}
