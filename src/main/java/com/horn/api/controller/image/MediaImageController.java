package com.horn.api.controller.image;

import com.horn.api.dto.retrieve.ImageDTO;
import com.horn.api.helper.FileHelper;
import com.horn.api.model.image.MediaImage;
import com.horn.api.service.image.MediaImageService;
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
@RequestMapping("/images")
@Slf4j
@CrossOrigin("*")
public class MediaImageController {
    @Autowired
    private MediaImageService mediaImageService;

    @Autowired
    private Mapper mapper;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ImageDTO> getImageDTO(@PathVariable("id") final long id) {
        try {
            MediaImage file = mediaImageService.getImage(id).get();
            return ResponseEntity.ok(mapper.toDto(file));
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/blob/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getImageBlob(@PathVariable("id") final long id) {
        String file = mediaImageService.getImage(id).get().getFile().getPath();
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

    @GetMapping("/{id}/thumbnail")
    @ResponseBody
    public ResponseEntity<byte[]> getImageThumbnail(@PathVariable("id") final long id) {
        String file = mediaImageService.getImage(id).get().getFile().getThumbnail().getPath();
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

    @PostMapping("/thumbnails/generate")
    @ResponseBody
    @Async
    public void getImageThumbnail() {
        mediaImageService.generateThumbnail();
    }

    @GetMapping
    public ResponseEntity<List<ImageDTO>> getImages() {
        return ResponseEntity.ok(mediaImageService.getImages());
    }
}
