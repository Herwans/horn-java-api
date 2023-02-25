package com.horn.api.controller.video;

import com.horn.api.dto.retrieve.PlaylistDTO;
import com.horn.api.model.body.PlaylistBody;
import com.horn.api.model.video.Playlist;
import com.horn.api.service.video.PlaylistService;
import com.horn.api.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {
	@Autowired
	private PlaylistService playlistService;

	@Autowired
	private Mapper mapper;
	
	@PostMapping
	public ResponseEntity<PlaylistDTO> addPlaylist(@RequestBody final String title) {
		return ResponseEntity.ok(playlistService.create(new Playlist(title)));
	}
	
	@DeleteMapping("/{id}")
	public void deletePlaylist(@PathVariable final Long id) {
		playlistService.delete(id);
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<PlaylistDTO> addVideo(@PathVariable("id") final Long playlistId, @RequestBody PlaylistBody body) {
		return ResponseEntity.ok(playlistService.addVideo(playlistId, body));
	}
	
	@DeleteMapping("/{id}/video")
	public ResponseEntity<PlaylistDTO> removeVideo(@PathVariable("id") final Long playlistId, @RequestBody Long videoId) {
		return ResponseEntity.ok(playlistService.removeVideo(playlistId, videoId));
	}

	@GetMapping
	public ResponseEntity<List<PlaylistDTO>> getPlaylists() {
		return ResponseEntity.ok(playlistService.findAllPlaylists());
	}
}
