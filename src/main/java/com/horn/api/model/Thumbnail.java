package com.horn.api.model;

import com.drew.lang.annotations.NotNull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "thumbnail")
public class Thumbnail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String path;
    @OneToOne(mappedBy = "thumbnail", optional = false)
    private MediaFile file;
}