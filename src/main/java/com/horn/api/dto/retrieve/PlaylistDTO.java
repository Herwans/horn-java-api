package com.horn.api.dto.retrieve;

import com.horn.api.model.MediaVideo;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class PlaylistDTO {
    public Long id;
    public HashSet<SagaDTO> sagas;
    public String title;
    public LinkedHashSet<MediaVideo> videos;
}
