package com.horn.api.service.video;

import com.horn.api.dto.retrieve.VideoDTO;
import com.horn.api.helper.FileHelper;
import com.horn.api.model.MediaFile;
import com.horn.api.model.video.MediaVideo;
import com.horn.api.repository.FileRepository;
import com.horn.api.repository.video.MediaVideoRepository;
import com.horn.api.service.DirectoryService;
import com.horn.api.service.ExtensionService;
import com.horn.api.util.Mapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Data
@Service
@Slf4j
public class MediaVideoService {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private MediaVideoRepository repository;

    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private Mapper mapper;

    @Autowired
    private ExtensionService extensionService;

    public Optional<MediaVideo> getVideo(final Long id) {
        return repository.findById(id);
    }

    public List<VideoDTO> getVideos() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public void deleteVideo(final Long id) {
        repository.deleteById(id);
    }

    public MediaVideo saveVideo(MediaVideo medium) {
        return repository.save(medium);
    }

    public MediaVideo saveVideo(MediaFile file) throws IOException, TikaException, SAXException {
        if (!FileHelper.isVideo(file.getPath())) {
            throw new IOException("Is not an image");
        }

        MediaVideo video = new MediaVideo();
        video.setTitle(file.getName());
        video.setFile(file);
        return repository.save(video);
    }

    public void updateVideosLength() {
        // TODO check if not already set
        for (MediaVideo video : getRepository().findAllByDurationIsNull()) {
            if (video.getDuration() == null) {
                try {
                    video.setDuration(FileHelper.videoLength(video.getFile().getPath()));
                } catch (SAXException | IOException | TikaException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    public MediaVideo saveVideo(MediaFile file, String title) throws IOException, TikaException, SAXException {
        if (!FileHelper.isVideo(file.getPath())) {
            throw new IOException("Is not an image");
        }

        MediaVideo video = new MediaVideo();
        video.setTitle(title);
        video.setFile(file);
        video.setDuration(FileHelper.videoLength(file.getPath()));
        return repository.save(video);
    }

    public List<MediaVideo> saveAllVideos(List<MediaVideo> media) {
        return repository.saveAll(media);
    }
}
