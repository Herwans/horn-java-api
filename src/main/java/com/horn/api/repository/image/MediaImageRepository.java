package com.horn.api.repository.image;

import com.horn.api.model.image.MediaImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaImageRepository extends JpaRepository<MediaImage, Long> {

}
