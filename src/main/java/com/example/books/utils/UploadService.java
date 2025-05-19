package com.example.books.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service

public class UploadService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    public String upload(MultipartFile file) throws IOException {
        try {
            Path filePath = Paths.get(uploadDir);
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath);
            }
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String original = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
            String fileName = original + "_" + System.currentTimeMillis() + extension;
            Files.copy(file.getInputStream(), filePath.resolve(fileName));
            return fileName;

        } catch (Exception e) {
            throw new IOException("Failed to upload file: " + e.getMessage(), e);
        }

    }

    public Resource loadFile(String fileName) throws IOException {
        try {
            Path filPath = Paths.get(uploadDir).resolve(fileName);
            Resource resource = new UrlResource(filPath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new IOException("Could not read the file: " + fileName);
            }

        } catch (Exception e) {
            throw new IOException("Failed to load file: " + e.getMessage(), e);

        }

    }

}
