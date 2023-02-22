package com.horn.api.model.video;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.horn.api.model.MediaVideo;
import com.horn.api.model.id.PlaylistVideosPk;

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
@IdClass(PlaylistVideosPk.class)
@Table(name="playlist_videos", uniqueConstraints = {@UniqueConstraint(columnNames = {"playlist_id", "position"})})
@AllArgsConstructor
@NoArgsConstructor
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
}