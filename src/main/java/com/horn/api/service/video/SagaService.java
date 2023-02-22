package com.horn.api.service.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horn.api.model.body.SagaBody;
import com.horn.api.model.video.Playlist;
import com.horn.api.model.video.Saga;
import com.horn.api.model.video.SagaPlaylists;
import com.horn.api.repository.video.PlaylistRepository;
import com.horn.api.repository.video.SagaPlaylistsRepository;
import com.horn.api.repository.video.SagaRepository;

@Service
public class SagaService {
	@Autowired
	private SagaRepository repository; 
	
	@Autowired
	private PlaylistRepository playlistRepository; 
	
	@Autowired
	private SagaPlaylistsRepository mapRepository; 
	
	public Saga create(Saga saga) {
		return repository.save(saga);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public Saga addPlaylist(Long sagaId, SagaBody body) {
		Saga saga = repository.findById(sagaId).get();
		Playlist playlist = playlistRepository.findById(body.playlistId()).get();
		
		mapRepository.save(new SagaPlaylists(saga, playlist, body.position()));
		
		return saga;
	}

	public Saga removePlaylist(Long sagaId, Long playlistId) {
		Saga saga = repository.findById(sagaId).get();
		mapRepository.deleteBySagaIdAndPlaylistId(sagaId, playlistId);
		
		return saga;
	}
}
