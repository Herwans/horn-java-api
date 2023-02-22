package com.horn.api.model.id;


import java.io.Serializable;
import java.util.Objects;

import com.horn.api.model.MediaVideo;
import com.horn.api.model.video.Playlist;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class PlaylistVideosPk implements Serializable {
	private static final long serialVersionUID = 6959751127070223172L;
	private MediaVideo video;
	private Playlist playlist;
	
	@Override
	public int hashCode() {
		return Objects.hash(playlist, video);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlaylistVideosPk other = (PlaylistVideosPk) obj;
		return Objects.equals(playlist, other.playlist) && Objects.equals(video, other.video);
	}
	
	
}