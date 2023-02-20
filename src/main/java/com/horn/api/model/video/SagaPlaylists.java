package com.horn.api.model.video;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name="saga_playlists", uniqueConstraints = {@UniqueConstraint(columnNames = {"saga_id", "position"})})
public class SagaPlaylists {
	@Id
	@ManyToOne
	@JoinColumn(nullable = false)
	private Playlist playlist;
	
	@Id
	@ManyToOne
	@JoinColumn(nullable = false)
	private Saga saga;
	
	private String title;
	
	private Integer position;
}
