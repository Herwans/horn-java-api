package com.horn.api.repository.video;

import com.horn.api.model.video.MediaVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaVideoRepository extends JpaRepository<MediaVideo, Long> {
    List<MediaVideo> findAllByDurationIsNull();
}
