package com.horn.api.model.video;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.horn.api.model.MediaFile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "media_video")
public class MediaVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private MediaFile file;

    private String title;

    private Long duration;

    @OneToMany(
            mappedBy = "video",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnoreProperties("video")
    @ToString.Exclude
    private List<PlaylistVideos> maps = new ArrayList<PlaylistVideos>();

    // TODO add language management

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MediaVideo that = (MediaVideo) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
