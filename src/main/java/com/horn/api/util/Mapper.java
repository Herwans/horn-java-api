package com.horn.api.util;

import com.horn.api.dto.retrieve.ImageDTO;
import com.horn.api.dto.retrieve.PlaylistDTO;
import com.horn.api.dto.retrieve.SagaDTO;
import com.horn.api.dto.retrieve.VideoDTO;
import com.horn.api.helper.FileHelper;
import com.horn.api.model.image.MediaImage;
import com.horn.api.model.video.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

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

    public ImageDTO toDto(MediaImage image) {
        ImageDTO result = new ImageDTO();

        result.id = image.getId();
        result.path = image.getFile().getPath();
        result.title = image.getTitle();
        result.createdAt = image.getFile().getCreatedAt();
        try {
            result.thumbnail = Base64.getEncoder().encodeToString(FileHelper.fileToByte(image.getFile().getThumbnail().getPath()));
        } catch (Exception e) {
            log.error(e.getMessage());
            result.thumbnail = null;
        }

        return result;
    }
}
