package com.horn.api.model.video;

import com.horn.api.model.MediaVideo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name="playlist_videos", uniqueConstraints = {@UniqueConstraint(columnNames = {"playlist_id", "position"})})
public class PlaylistVideos {
	
	@Id
	@ManyToOne
	@JoinColumn(nullable = false)
	private MediaVideo video;
	
	@Id
	@ManyToOne
	@JoinColumn(nullable = false)
	private Playlist playlist;
	
	private Integer position;
}
