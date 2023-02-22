package com.horn.api.repository.video;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.horn.api.model.video.Saga;

@Repository
public interface SagaRepository extends CrudRepository<Saga, Long>{
}
