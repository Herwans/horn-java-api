package com.horn.api.controller.video;

import com.horn.api.dto.retrieve.VideoDTO;
import com.horn.api.helper.FileHelper;
import com.horn.api.model.video.MediaVideo;
import com.horn.api.service.video.MediaVideoService;
import com.horn.api.util.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videos")
@Slf4j
@CrossOrigin("*")
public class VideoController {
    @Autowired
    private MediaVideoService mediaVideoService;

    @Autowired
    private Mapper mapper;

    @GetMapping
    public ResponseEntity<List<VideoDTO>> getVideos() {
        return ResponseEntity.ok(mediaVideoService.getVideos());
    }

    @GetMapping("/blob/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getVideoBlob(@PathVariable("id") final long id) {
        String file = mediaVideoService.getVideo(id).get().getFile().getPath();
        MediaType contentType = new MediaType(MimeTypeUtils.parseMimeType(FileHelper.getMediaMimeType(file)));

        try {
            byte[] input = FileHelper.fileToByte(file);
            return ResponseEntity.ok().contentType(contentType).body(input);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<VideoDTO> getVideoDTO(@PathVariable("id") final long id) {
        try {
            MediaVideo file = mediaVideoService.getVideo(id).get();
            return ResponseEntity.ok(mapper.toDto(file));
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/update-length")
    @Async
    public void getVideoDTO() {
        mediaVideoService.updateVideosLength();
    }

}
