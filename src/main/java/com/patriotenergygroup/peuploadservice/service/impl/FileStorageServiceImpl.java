package com.patriotenergygroup.peuploadservice.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.patriotenergygroup.peuploadservice.configuration.StorageProperties;
import com.patriotenergygroup.peuploadservice.exception.StorageException;
import com.patriotenergygroup.peuploadservice.exception.StorageFileNotFoundException;
import com.patriotenergygroup.peuploadservice.service.StorageService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;;

@Service("storageService")
public class FileStorageServiceImpl implements StorageService {

	private final Path rootLocation;
	
	@Autowired
	public FileStorageServiceImpl(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}
	
	@Override
	public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
	}

	@Override
	public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Path path = this.createStoragePath(file);
            Files.copy(file.getInputStream(), 
            		path, StandardCopyOption.REPLACE_EXISTING);
            return path.getFileName().toString();
            
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
	}

	@Override
	public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
	}

	@Override
	public Path load(String filename) {
        return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
	}
	
	@Override
	public void deleteFile(Path file) {
		FileSystemUtils.deleteRecursively(file.toFile());
	}

	@Override
	public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}
	
	private Path createStoragePath(MultipartFile file) {
		String updatedFileName = 
				FilenameUtils.getExtension(file.getOriginalFilename()).isEmpty() 
					? String.format("%s.%s",
						FilenameUtils.removeExtension(file.getOriginalFilename()),
						DateTimeFormatter.ofPattern("yyyyMMdd.HHmmss").format(LocalDateTime.now()))
					: String.format("%s.%s.%s",
							FilenameUtils.removeExtension(file.getOriginalFilename()),
							DateTimeFormatter.ofPattern("yyyyMMdd.HHmmss").format(LocalDateTime.now()),
							FilenameUtils.getExtension(file.getOriginalFilename()));
				
		return this.rootLocation.resolve(updatedFileName);
	}
	
}
