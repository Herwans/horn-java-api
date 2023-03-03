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
@Table(name = "saga")
@AllArgsConstructor
public class Saga {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;

    @OneToMany(
            mappedBy = "saga",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnoreProperties("saga")
    @ToString.Exclude
    private List<SagaPlaylists> maps = new ArrayList<SagaPlaylists>();

    public Saga(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Saga saga = (Saga) o;
        return getId() != null && Objects.equals(getId(), saga.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}