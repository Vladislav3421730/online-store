package com.example.webapp.service.impl;

import com.example.webapp.dto.ImageDto;
import com.example.webapp.exception.ImageNotFoundException;
import com.example.webapp.mapper.ImageMapper;
import com.example.webapp.mapper.ImageMapperImpl;
import com.example.webapp.model.Image;

import com.example.webapp.repository.ImageRepository;
import com.example.webapp.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    private final ImageMapper imageMapper = new ImageMapperImpl();

    @Override
    public ImageDto findById(Long id) {
        log.info("Fetching image by ID: {}", id);
        Image image = imageRepository.findById(id).orElseThrow(() -> {
            log.error("Image with id {} not found", id);
            return new ImageNotFoundException("Image with id " + id + " was not found");
        });
        log.info("Found image with ID: {}", id);
        return imageMapper.toDTO(image);
    }
}
