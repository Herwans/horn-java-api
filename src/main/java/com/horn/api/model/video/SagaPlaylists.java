package com.horn.api.model.video;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.horn.api.model.id.SagaPlaylistsPk;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@IdClass(SagaPlaylistsPk.class)
@Table(name = "saga_playlists", uniqueConstraints = {@UniqueConstraint(columnNames = {"saga_id", "position"})})
@AllArgsConstructor
public class SagaPlaylists {

    @Id
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(nullable = false)
    @JsonIgnoreProperties("maps")
    private Saga saga;

    @Id
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(nullable = false)
    @JsonIgnoreProperties("maps_saga")
    private Playlist playlist;

    private Integer position;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SagaPlaylists that = (SagaPlaylists) o;
        return getSaga() != null && Objects.equals(getSaga(), that.getSaga())
                && getPlaylist() != null && Objects.equals(getPlaylist(), that.getPlaylist());
    }

    @Override
    public int hashCode() {
        return Objects.hash(saga, playlist);
    }
}