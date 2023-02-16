package com.horn.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.horn.api.model.File;

@Repository
public interface FileRepository  extends CrudRepository<File, Long>{

}
