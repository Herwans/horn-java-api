package com.horn.api.repository.video;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.horn.api.model.video.Playlist;

@Repository
public interface PlaylistRepository  extends CrudRepository<Playlist, Long>{
	
}
