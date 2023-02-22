package com.horn.api.repository.video;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.horn.api.model.video.SagaPlaylists;

@Repository
public interface SagaPlaylistsRepository extends CrudRepository<SagaPlaylists, Long>{
	@Transactional
	void deleteBySagaIdAndPlaylistId(Long sagaId, Long playlistId);
}
