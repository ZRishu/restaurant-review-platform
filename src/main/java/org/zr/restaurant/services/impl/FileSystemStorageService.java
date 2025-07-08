package org.zr.restaurant.services.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.zr.restaurant.exceptions.StorageException;
import org.zr.restaurant.services.StorageService;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
@Slf4j
public class FileSystemStorageService implements StorageService {

    @Value("${app.storage.location:uploads}")
    private String storageLocation;

    private Path rootLocation;

    @PostConstruct
    public void init() {
        rootLocation = Paths.get(storageLocation).toAbsolutePath().normalize();
        try {
            Files.createDirectories(rootLocation);
            log.info("Storage location initialized: {}", rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }
    }

    @Override
    public String store(MultipartFile file, String fileName) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Cannot save an empty file");
            }

            String cleanFileName = StringUtils.cleanPath(fileName);
            if (cleanFileName.contains("..")) {
                throw new StorageException("Cannot store file with relative path outside current directory");
            }

            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String finalFileName = cleanFileName  + "." + extension;

            Path destinationFile = rootLocation
                    .resolve(finalFileName)
                    .normalize()
                    .toAbsolutePath();
            if (!destinationFile.getParent().startsWith(rootLocation)) {
                throw new StorageException("Cannot store file outside specified directory");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                log.info("File stored successfully: {}", finalFileName);
            }

            return finalFileName;

        } catch (IOException e) {
            throw new StorageException("Failed to store file", e);
        }
    }

    @Override
    public Optional<Resource> loadAsResource(String filename) {
        try {
            String cleanFilename = StringUtils.cleanPath(filename);
            if (cleanFilename.contains("..")) {
                log.warn("Attempted to access file with relative path: {}", filename);
                return Optional.empty();
            }

            Path file = rootLocation.resolve(cleanFilename).normalize().toAbsolutePath();

            if (!file.startsWith(rootLocation)) {
                log.warn("Attempted to access file outside storage directory: {}", filename);
                return Optional.empty();
            }

            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return Optional.of(resource);
            } else {
                log.warn("File not found or not readable: {}", filename);
                return Optional.empty();
            }
        } catch (MalformedURLException e) {
            log.warn("Could not read file {}", filename, e);
            return Optional.empty();
        }
    }

}
