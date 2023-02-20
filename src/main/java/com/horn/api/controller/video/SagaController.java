package com.horn.api.controller.video;

import com.horn.api.dto.retrieve.SagaDTO;
import com.horn.api.model.body.SagaBody;
import com.horn.api.model.video.Saga;
import com.horn.api.service.video.SagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sagas")
public class SagaController {
    @Autowired
    private SagaService sagaService;

    @PostMapping
    public ResponseEntity<SagaDTO> addPlaylist(@RequestBody final String title) {
        return ResponseEntity.ok(sagaService.create(new Saga(title)));
    }

    @DeleteMapping("/{id}")
    public void deletePlaylist(@PathVariable final Long id) {
        sagaService.delete(id);
    }
	
    @PostMapping("/{id}")
    public ResponseEntity<SagaDTO> addVideo(@PathVariable("id") final Long sagaId, @RequestBody SagaBody body) {
        return ResponseEntity.ok(sagaService.addPlaylist(sagaId, body));
    }

    @DeleteMapping("/{id}/playlist")
    public ResponseEntity<SagaDTO> removeVideo(@PathVariable("id") final Long sagaId, @RequestBody Long playlistId) {
        return ResponseEntity.ok(sagaService.removePlaylist(sagaId, playlistId));
    }

    @GetMapping
    public ResponseEntity<List<SagaDTO>> getAll() {
        return ResponseEntity.ok(sagaService.getAll());
    }
}
