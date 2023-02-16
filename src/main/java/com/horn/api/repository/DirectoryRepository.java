package com.horn.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.horn.api.model.Directory;

@Repository
public interface DirectoryRepository  extends CrudRepository<Directory, Long>{

}
