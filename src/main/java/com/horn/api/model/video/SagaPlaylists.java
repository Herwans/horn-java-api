package com.horn.api.model.video;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.horn.api.model.id.SagaPlaylistsPk;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@IdClass(SagaPlaylistsPk.class)
@Table(name="saga_playlists", uniqueConstraints = {@UniqueConstraint(columnNames = {"saga_id", "position"})})
@AllArgsConstructor
@NoArgsConstructor
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
}