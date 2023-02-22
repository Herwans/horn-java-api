package com.horn.api.service.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horn.api.model.MediaVideo;
import com.horn.api.model.body.PlaylistBody;
import com.horn.api.model.video.Playlist;
import com.horn.api.model.video.PlaylistVideos;
import com.horn.api.repository.video.PlaylistRepository;
import com.horn.api.repository.video.PlaylistVideosRepository;
import com.horn.api.service.MediaVideoService;

@Service
public class PlaylistService {
	@Autowired
	private PlaylistRepository repository; 
	
	@Autowired
	private PlaylistVideosRepository mapRepository; 
	
	@Autowired
	private MediaVideoService videoService;
	
	public Playlist create(Playlist playlist) {
		return repository.save(playlist);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public Playlist addVideo(Long playlistId, PlaylistBody body) {
		MediaVideo video = videoService.getMedium(body.videoId()).get();
		Playlist playlist = repository.findById(playlistId).get();
		
		mapRepository.save(
				new PlaylistVideos(video, playlist, body.position()));
		
		return playlist;
	}

	public Playlist removeVideo(Long playlistId, Long videoId) {
		Playlist playlist = repository.findById(playlistId).get();
		mapRepository.deleteByPlaylistIdAndVideoId(playlistId, videoId);
		
		return playlist;
	}
}
