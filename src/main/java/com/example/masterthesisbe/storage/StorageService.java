package com.example.masterthesisbe.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

	void store(MultipartFile file);
	void store(MultipartFile file, String filename);

	Path load(String filename);

	Resource loadAsResource(String filename);

	void delete(String filename);
}
