package com.horn.api.controller.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.horn.api.model.body.PlaylistBody;
import com.horn.api.model.video.Playlist;
import com.horn.api.service.video.PlaylistService;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {
	@Autowired
	private PlaylistService playlistService;
	
	@PostMapping
	public ResponseEntity<Playlist> addPlaylist(@RequestBody final String title) {
		return ResponseEntity.ok(playlistService.create(new Playlist(title)));
	}
	
	@DeleteMapping("/{id}")
	public void deletePlaylist(@PathVariable final Long id) {
		playlistService.delete(id);
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<Playlist> addVideo(@PathVariable("id") final Long playlistId, @RequestBody PlaylistBody body) {
		return ResponseEntity.ok(playlistService.addVideo(playlistId, body));
	}
	
	@DeleteMapping("/{id}/video")
	public ResponseEntity<Playlist> removeVideo(@PathVariable("id") final Long playlistId, @RequestBody Long videoId) {
		return ResponseEntity.ok(playlistService.removeVideo(playlistId, videoId));
	}
}
