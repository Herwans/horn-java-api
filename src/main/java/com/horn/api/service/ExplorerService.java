package com.horn.api.service;

import com.horn.api.helper.FileHelper;
import com.horn.api.model.MediaFile;
import com.horn.api.service.video.MediaVideoService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Service
@Slf4j
public class ExplorerService {
    @Autowired
    private FileService fileService;

    @Autowired
    private MediaImageService mediaImageService;

    @Autowired
    private MediaVideoService mediaVideoService;

    public List<Path> getAllFilesAsPath(String startingPoint) throws IOException {
        List<Path> result = new ArrayList<Path>();
        Path rootPath = Path.of(startingPoint);

        Iterator<Path> itr = Files.walk(rootPath).iterator();
        while (true) {
            try {
                if (itr.hasNext()) {
                    Path current = itr.next();
                    if (Files.isRegularFile(current)) {
                        result.add(current);
                    }

                } else {
                    break;
                }
            } catch (Exception e) {
                log.error(e.getLocalizedMessage());
            }
        }
        return result;
    }

    public void walkAndSave(String startingPoint) throws IOException {
        //List<String> result = new ArrayList<String>();
        Path rootPath = Path.of(startingPoint);
        Iterator<Path> itr = Files.walk(rootPath).iterator();
        while (true) {
            try {
                if (itr.hasNext()) {
                    Path current = itr.next();
                    if (Files.isRegularFile(current) && FilenameUtils.getBaseName(current.toString()) != "") {
                        fileService.saveFile(current.toString());
                    }
                } else {
                    break;
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    public void sortFile(MediaFile file) throws IOException, TikaException, SAXException {
        if (FileHelper.isImage(file.getPath())) {
            mediaImageService.saveMedia(file);
        } else if (FileHelper.isVideo(file.getPath())) {
            mediaVideoService.saveVideo(file);
        }
    }

    public void sortFiles() {
        for (MediaFile file : fileService.getUnsortedFiles()) {
            try {
                sortFile(file);
            } catch (Exception e) {
                log.error("File skipped : " + file.getPath());
            }
        }
    }
}
