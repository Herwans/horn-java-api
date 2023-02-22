package com.horn.api.model.video;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="playlist")
@AllArgsConstructor
@NoArgsConstructor
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
	private List<PlaylistVideos> maps = new ArrayList<PlaylistVideos>();
	
	@OneToMany(
		    mappedBy = "playlist",
		    cascade = CascadeType.ALL,
		    orphanRemoval = true
		)
    @JsonIgnoreProperties("playlist")
	private List<SagaPlaylists> maps_saga = new ArrayList<SagaPlaylists>();
	
	public Playlist(String title) {
		this.title = title;
	}
}
