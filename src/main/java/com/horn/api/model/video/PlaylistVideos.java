package com.horn.api.model.video;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.horn.api.model.id.PlaylistVideosPk;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@IdClass(PlaylistVideosPk.class)
@Table(name = "playlist_videos", uniqueConstraints = {@UniqueConstraint(columnNames = {"playlist_id", "position"})})
@AllArgsConstructor
public class PlaylistVideos {

    @Id
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(nullable = false)
    @JsonIgnoreProperties("maps")
    private MediaVideo video;

    @Id
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(nullable = false)
    @JsonIgnoreProperties("maps")
    private Playlist playlist;

    private Integer position;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PlaylistVideos that = (PlaylistVideos) o;
        return getVideo() != null && Objects.equals(getVideo(), that.getVideo())
                && getPlaylist() != null && Objects.equals(getPlaylist(), that.getPlaylist());
    }

    @Override
    public int hashCode() {
        return Objects.hash(video, playlist);
    }
}