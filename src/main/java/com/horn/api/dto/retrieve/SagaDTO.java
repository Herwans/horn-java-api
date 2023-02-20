package com.horn.api.dto.retrieve;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;

@Setter
@Getter
public class SagaDTO {
    public Long id;
    public String title;
    public LinkedHashSet<PlaylistDTO> playlists;
}
