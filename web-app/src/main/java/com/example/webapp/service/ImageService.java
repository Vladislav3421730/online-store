package com.example.webapp.service;

import com.example.webapp.dto.ImageDto;

public interface ImageService {

    ImageDto findById(Long id);
}
