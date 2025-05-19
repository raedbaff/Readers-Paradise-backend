package com.example.books.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.books.utils.UploadService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)

@RequestMapping("/api/media")
public class MediaController {
    @Autowired
    UploadService uploadService;

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getMedia(@PathVariable String fileName) {
        try {
            Resource resource = uploadService.loadFile(fileName);
            Path path = resource.getFile().toPath();
            String contentType = Files.probeContentType(path);
            if (contentType == null) {
                contentType = "application/octet-stream"; // fallback
            }
            return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
            .contentType(MediaType.parseMediaType(contentType))
            .body(resource);
            


        } catch (IOException e) {
            return null;

        }

    }

}
