package com.example.webapp.controller;

import com.example.webapp.dto.ImageDto;
import com.example.webapp.service.ImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ImageController {

    ImageService imageService;

    @GetMapping("/images/{id}")
    public ResponseEntity<?> findImageById(@PathVariable Long id) {
        ImageDto image = imageService.findById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.getContentType()))
                .body(image.getBytes());
    }
}
