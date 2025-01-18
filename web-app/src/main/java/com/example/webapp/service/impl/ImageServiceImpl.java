package com.example.webapp.service.impl;

import com.example.webapp.dto.ImageDto;
import com.example.webapp.exception.ImageNotFoundException;
import com.example.webapp.mapper.ImageMapper;
import com.example.webapp.mapper.ImageMapperImpl;
import com.example.webapp.model.Image;
import com.example.webapp.dao.impl.ImageDaoImpl;
import com.example.webapp.service.ImageService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final static ImageServiceImpl INSTANCE = new ImageServiceImpl();

    public static ImageServiceImpl getInstance() {
        return INSTANCE;
    }

    ImageDaoImpl imageRepository = ImageDaoImpl.getInstance();
    ImageMapper imageMapper = new ImageMapperImpl();

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
