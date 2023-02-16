package com.horn.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.horn.api.model.Extension;

@Repository
public interface ExtensionRepository  extends CrudRepository<Extension, Long>{

}
