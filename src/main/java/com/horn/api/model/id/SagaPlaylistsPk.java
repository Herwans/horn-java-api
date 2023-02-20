package com.horn.api.model.id;


import java.io.Serializable;
import java.util.Objects;

import com.horn.api.model.video.Playlist;
import com.horn.api.model.video.Saga;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class SagaPlaylistsPk implements Serializable {
	private static final long serialVersionUID = 1L;
	private Saga saga;
	private Playlist playlist;
	
	@Override
	public int hashCode() {
		return Objects.hash(playlist, saga);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SagaPlaylistsPk other = (SagaPlaylistsPk) obj;
		return Objects.equals(playlist, other.playlist) && Objects.equals(saga, other.saga);
	}
}