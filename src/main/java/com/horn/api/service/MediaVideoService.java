package com.horn.api.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.horn.api.helper.FileHelper;
import com.horn.api.model.File;
import com.horn.api.model.MediaVideo;
import com.horn.api.model.video.Playlist;
import com.horn.api.repository.FileRepository;
import com.horn.api.repository.MediaVideoRepository;
import com.horn.api.repository.video.PlaylistRepository;

import lombok.Data;

@Data
@Service
public class MediaVideoService {
	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private MediaVideoRepository repository;

	@Autowired
	private DirectoryService directoryService;

	@Autowired
	private ExtensionService extensionService;

	public Optional<MediaVideo> getMedium(final Long id) {
		return repository.findById(id);
	}

	public Iterable<MediaVideo> getMedia() {
		return repository.findAll();
	}

	public void deleteMedia(final Long id) {
		repository.deleteById(id);
	}

	public MediaVideo saveMedia(MediaVideo medium) {
		return repository.save(medium);
	}

	public MediaVideo saveMedia(File file) throws IOException {
		if (!FileHelper.isVideo(file.getPath())) {
			throw new IOException("Is not an image");
		}

		MediaVideo medium = new MediaVideo();
		medium.setTitle(file.getName());
		medium.setFile(file);

		return repository.save(medium);
	}
	
	public MediaVideo saveMedia(File file, String title) throws IOException {
		if (!FileHelper.isVideo(file.getPath())) {
			throw new IOException("Is not an image");
		}

		MediaVideo medium = new MediaVideo();
		medium.setTitle(title);
		medium.setFile(file);

		return repository.save(medium);
	}

	public void saveAllFiles(List<MediaVideo> media) {
		repository.saveAll(media);
	}
}
