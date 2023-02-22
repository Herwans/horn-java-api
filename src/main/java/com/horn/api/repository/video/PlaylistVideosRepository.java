package com.horn.api.repository.video;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.horn.api.model.video.PlaylistVideos;

@Repository
public interface PlaylistVideosRepository  extends CrudRepository<PlaylistVideos, Long>{	
	@Transactional
	void deleteByPlaylistIdAndVideoId(Long playlistId, Long videoId);
}
