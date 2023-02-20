package com.horn.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "directory")
public class Directory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(name = "is_managed")
    private boolean isManaged = true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Directory directory = (Directory) o;
        return getId() != null && Objects.equals(getId(), directory.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}