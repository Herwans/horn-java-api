package com.horn.api.service;

import com.horn.api.helper.FileHelper;
import com.horn.api.model.MediaFile;
import com.horn.api.model.MediaImage;
import com.horn.api.model.util.Dimension;
import com.horn.api.repository.FileRepository;
import com.horn.api.repository.MediaImageRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class MediaImageService {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private MediaImageRepository repository;

    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private ExtensionService extensionService;

    public Optional<MediaImage> getMedium(final Long id) {
        return repository.findById(id);
    }

    public Iterable<MediaImage> getMedia() {
        return repository.findAll();
    }

    public void deleteMedia(final Long id) {
        repository.deleteById(id);
    }

    public MediaImage saveMedia(MediaImage medium) {
        return repository.save(medium);
    }

    public MediaImage saveMedia(MediaFile file) throws IOException {
        java.io.File ioFile = new java.io.File(file.getPath());
        if (!FileHelper.isImage(file.getPath())) {
            throw new IOException("Is not an image");
        }

        MediaImage medium = new MediaImage();

        medium.setFile(file);
        Dimension d = FileHelper.getImageDimension(ioFile);
        medium.setWidth(d.getWidth());
        medium.setHeight(d.getHeight());

        return repository.save(medium);
    }

    public void saveAllFiles(List<MediaImage> media) {
        repository.saveAll(media);
    }
}
