package com.horn.api.service.video;

import com.horn.api.dto.retrieve.PlaylistDTO;
import com.horn.api.model.body.PlaylistBody;
import com.horn.api.model.video.MediaVideo;
import com.horn.api.model.video.Playlist;
import com.horn.api.model.video.PlaylistVideos;
import com.horn.api.repository.video.PlaylistRepository;
import com.horn.api.repository.video.PlaylistVideosRepository;
import com.horn.api.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository repository;

    @Autowired
    private PlaylistVideosRepository mapRepository;

    @Autowired
    private MediaVideoService videoService;

    @Autowired
    private Mapper mapper;

    public PlaylistDTO create(Playlist playlist) {
        return mapper.toDto(repository.save(playlist));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public PlaylistDTO addVideo(Long playlistId, PlaylistBody body) {
        MediaVideo video = videoService.getVideo(body.videoId()).get();
        Playlist playlist = repository.findById(playlistId).get();

        mapRepository.save(new PlaylistVideos(video, playlist, body.position()));

        return mapper.toDto(playlist);
    }

    public PlaylistDTO removeVideo(Long playlistId, Long videoId) {
        Playlist playlist = repository.findById(playlistId).get();
        mapRepository.deleteByPlaylistIdAndVideoId(playlistId, videoId);

        return mapper.toDto(playlist);
    }

    public List<PlaylistDTO> findAllPlaylists() {
        return repository.findAll().stream().map(mapper::toDto).collect(toList());
    }
}
