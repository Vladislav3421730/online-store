package com.example.webapp.controller;

import com.example.webapp.dto.ImageDto;
import com.example.webapp.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/images/{id}")
    public ResponseEntity<?> findImageById(@PathVariable Long id) {
        ImageDto image = imageService.findById(id);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.getContentType()))
                .body(image.getBytes());
    }
}
