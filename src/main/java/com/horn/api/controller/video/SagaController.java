package com.horn.api.controller.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.horn.api.model.body.SagaBody;
import com.horn.api.model.video.Saga;
import com.horn.api.service.video.SagaService;

@RestController
@RequestMapping("/sagas")
public class SagaController {
	@Autowired
	private SagaService sagaService;
	
	@PostMapping
	public ResponseEntity<Saga> addPlaylist(@RequestBody final String title) {
		return ResponseEntity.ok(sagaService.create(new Saga(title)));
	}
	
	@DeleteMapping("/{id}")
	public void deletePlaylist(@PathVariable final Long id) {
		sagaService.delete(id);
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<Saga> addVideo(@PathVariable("id") final Long sagaId, @RequestBody SagaBody body) {
		return ResponseEntity.ok(sagaService.addPlaylist(sagaId, body));
	}
	
	@DeleteMapping("/{id}/playlist")
	public ResponseEntity<Saga> removeVideo(@PathVariable("id") final Long sagaId, @RequestBody Long playlistId) {
		return ResponseEntity.ok(sagaService.removePlaylist(sagaId, playlistId));
	}
}
