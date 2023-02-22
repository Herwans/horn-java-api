package com.horn.api.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.horn.api.model.video.PlaylistVideos;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="media_video")
public class MediaVideo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
    @JoinColumn(unique = true)
	private File file;
	
	private String title;
	
	@OneToMany(
		    mappedBy = "video",
		    cascade = CascadeType.ALL,
		    orphanRemoval = true
		)
    @JsonIgnoreProperties("video")
	private List<PlaylistVideos> maps = new ArrayList<PlaylistVideos>();
	
	// TODO add language management
}
