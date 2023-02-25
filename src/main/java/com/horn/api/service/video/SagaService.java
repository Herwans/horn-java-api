package com.horn.api.service.video;

import com.horn.api.dto.retrieve.SagaDTO;
import com.horn.api.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horn.api.model.body.SagaBody;
import com.horn.api.model.video.Playlist;
import com.horn.api.model.video.Saga;
import com.horn.api.model.video.SagaPlaylists;
import com.horn.api.repository.video.PlaylistRepository;
import com.horn.api.repository.video.SagaPlaylistsRepository;
import com.horn.api.repository.video.SagaRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class SagaService {
	@Autowired
	private SagaRepository repository; 
	
	@Autowired
	private PlaylistRepository playlistRepository; 
	
	@Autowired
	private SagaPlaylistsRepository mapRepository; 

	@Autowired
	private Mapper mapper;

	public SagaDTO create(Saga saga) {
		return mapper.toDto(repository.save(saga));
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public SagaDTO addPlaylist(Long sagaId, SagaBody body) {
		Saga saga = repository.findById(sagaId).get();
		Playlist playlist = playlistRepository.findById(body.playlistId()).get();
		
		mapRepository.save(new SagaPlaylists(saga, playlist, body.position()));

		return mapper.toDto(saga);
	}

	public SagaDTO removePlaylist(Long sagaId, Long playlistId) {
		Saga saga = repository.findById(sagaId).get();
		mapRepository.deleteBySagaIdAndPlaylistId(sagaId, playlistId);
		
		return mapper.toDto(saga);
	}

	public List<SagaDTO> getAll() {
		return repository.findAll().stream().map(mapper::toDto).collect(toList());
	}
}
