package com.horn.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.horn.api.model.MediaImage;

@Repository
public interface MediaImageRepository  extends CrudRepository<MediaImage, Long>{
	
}
