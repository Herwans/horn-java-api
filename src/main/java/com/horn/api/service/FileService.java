package com.horn.api.service;

import co.elastic.thumbnails4j.core.Dimensions;
import co.elastic.thumbnails4j.core.Thumbnailer;
import co.elastic.thumbnails4j.core.ThumbnailingException;
import co.elastic.thumbnails4j.image.ImageThumbnailer;
import com.horn.api.model.Directory;
import com.horn.api.model.Extension;
import com.horn.api.model.MediaFile;
import com.horn.api.model.Thumbnail;
import com.horn.api.repository.FileRepository;
import com.horn.api.repository.ThumbnailRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
@Service
@Slf4j
public class FileService {
    @Autowired
    private Environment env;

    @Autowired
    private ThumbnailRepository thumbnailRepository;

    @Autowired
    private FileRepository repository;

    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private ExtensionService extensionService;

    public Optional<MediaFile> getFile(final Long id) {
        return repository.findById(id);
    }

    public Iterable<MediaFile> getFiles() {
        return repository.findAll();
    }

    public Iterable<MediaFile> getUnsortedFiles() {
        // TODO Implement it
        return repository.findAll();
    }

    public void deleteFile(final Long id) {
        repository.deleteById(id);
    }

    public MediaFile saveFile(String fileStr) {
        return repository.save(prepare(fileStr));
    }

    public void saveAllFiles(List<String> files) {
        List<MediaFile> result = new ArrayList<MediaFile>();
        for (String file : files) {
            result.add(prepare(file));
        }

        repository.saveAll(result);
    }

    private MediaFile prepare(String path) {
        MediaFile file = new MediaFile();
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

        return file;
    }

    public void generateThumbnail(MediaFile file) throws IOException {
        File thumbnailFile = new File(
                env.getProperty("horn.metadata.thumbnail.images"),
                "thumbnail-" + file.getId() + ".jpg");
        boolean alreadyExists = thumbnailFile.exists();
        if (file.getThumbnail() != null && alreadyExists) {
            return;
        } else if (file.getThumbnail() != null) {
            file.setThumbnail(null);
        } else if (alreadyExists) {
            Files.delete(Path.of(thumbnailFile.getPath()));
        }

        Thumbnailer thumbnailer = new ImageThumbnailer("jpg");
        List<Dimensions> outputDimensions = Collections.singletonList(new Dimensions(300, 300));
        try {
            BufferedImage output = thumbnailer.getThumbnails(new java.io.File(file.getPath()), outputDimensions).get(0);

            thumbnailFile.mkdirs();
            if (ImageIO.write(output, "jpg", thumbnailFile)) {
                Thumbnail t = new Thumbnail();
                t.setPath(thumbnailFile.getPath());
                file.setThumbnail(t);
                repository.save(file);
            }
        } catch (ThumbnailingException | IOException e) {
            log.error(e.getMessage());
        }
    }
}
