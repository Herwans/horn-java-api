package com.horn.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.horn.api.model.MediaVideo;

@Repository
public interface MediaVideoRepository  extends CrudRepository<MediaVideo, Long>{
	
}
