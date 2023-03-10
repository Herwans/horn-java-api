package com.horn.api.service.image;

import com.horn.api.dto.retrieve.ImageDTO;
import com.horn.api.helper.FileHelper;
import com.horn.api.model.MediaFile;
import com.horn.api.model.image.MediaImage;
import com.horn.api.model.util.Dimension;
import com.horn.api.repository.image.MediaImageRepository;
import com.horn.api.service.DirectoryService;
import com.horn.api.service.ExtensionService;
import com.horn.api.service.FileService;
import com.horn.api.util.Mapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Data
@Service
@Slf4j
public class MediaImageService {
    @Autowired
    private FileService fileService;

    @Autowired
    private MediaImageRepository repository;

    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private ExtensionService extensionService;

    @Autowired
    private Mapper mapper;

    public Optional<MediaImage> getImage(final Long id) {
        return repository.findById(id);
    }

    public List<ImageDTO> getImages() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public void deleteImage(final Long id) {
        repository.deleteById(id);
    }

    public MediaImage saveImage(MediaImage medium) {
        return repository.save(medium);
    }

    public MediaImage saveImage(MediaFile file) throws IOException {
        java.io.File ioFile = new java.io.File(file.getPath());
        if (!FileHelper.isImage(file.getPath())) {
            throw new IOException("Is not an image");
        }

        MediaImage image = new MediaImage();

        image.setFile(file);
        Dimension d = FileHelper.getImageDimension(ioFile);
        image.setWidth(d.getWidth());
        image.setHeight(d.getHeight());
        image.setTitle(null);
        return repository.save(image);
    }

    public void saveAllImages(List<MediaImage> media) {
        repository.saveAll(media);
    }

    public void generateThumbnail() {
        for (MediaImage image : repository.findAll()) {
            if (image.getFile().getThumbnail() == null) {
                try {
                    fileService.generateThumbnail(image.getFile());
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
}
