package com.example.webapp.repository;

import com.example.webapp.model.Image;

import java.util.Optional;

public interface ImageRepository {
    Optional<Image> findById(Long id);
}
