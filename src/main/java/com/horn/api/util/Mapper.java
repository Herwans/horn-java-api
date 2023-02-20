package com.horn.api.util;

import com.horn.api.dto.retrieve.PlaylistDTO;
import com.horn.api.dto.retrieve.SagaDTO;
import com.horn.api.dto.retrieve.VideoDTO;
import com.horn.api.model.video.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;

@Component
@Slf4j
public class Mapper {
    public PlaylistDTO toDto(Playlist playlist) {
        PlaylistDTO result = new PlaylistDTO();

        HashSet<SagaDTO> sagas = new HashSet<>();
        for (SagaPlaylists element : playlist.getMaps_saga()) {
            Saga r = element.getSaga();
            r.setMaps(new ArrayList<>());
            sagas.add(toDto(r));
        }

        LinkedHashSet<MediaVideo> videos = new LinkedHashSet<>();
        playlist.getMaps().sort(Comparator.comparing(PlaylistVideos::getPosition, Comparator.nullsLast(Comparator.naturalOrder())));
        for (PlaylistVideos element : playlist.getMaps()) {
            videos.add(element.getVideo());
        }

        result.title = playlist.getTitle();
        result.videos = videos;
        result.sagas = sagas;
        result.id = playlist.getId();

        return result;
    }

    public SagaDTO toDto(Saga saga) {
        SagaDTO result = new SagaDTO();

        LinkedHashSet<PlaylistDTO> playlists = new LinkedHashSet<>();
        saga.getMaps().sort(Comparator.comparing(SagaPlaylists::getPosition, Comparator.nullsLast(Comparator.naturalOrder())));
        for (SagaPlaylists element : saga.getMaps()) {
            Playlist e = element.getPlaylist();
            e.setMaps(new ArrayList<>());
            e.setMaps_saga(new ArrayList<>());
            playlists.add(toDto(e));
        }

        result.id = saga.getId();
        result.title = saga.getTitle();
        result.playlists = playlists;

        return result;
    }

    public VideoDTO toDto(MediaVideo video) {
        VideoDTO result = new VideoDTO();

        result.id = video.getId();
        result.path = video.getFile().getPath();
        result.title = video.getTitle();
        result.createdAt = video.getFile().getCreatedAt();
        result.duration = video.getDuration();
        result.thumbnail = null;

        return result;
    }
}
