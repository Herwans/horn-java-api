package com.horn.api.model.video;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "playlist")
@AllArgsConstructor
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;

    @OneToMany(
            mappedBy = "playlist",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnoreProperties("playlist")
    @ToString.Exclude
    private List<PlaylistVideos> maps = new ArrayList<PlaylistVideos>();

    @OneToMany(
            mappedBy = "playlist",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnoreProperties("playlist")
    @ToString.Exclude
    private List<SagaPlaylists> maps_saga = new ArrayList<SagaPlaylists>();

    public Playlist(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Playlist playlist = (Playlist) o;
        return getId() != null && Objects.equals(getId(), playlist.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
