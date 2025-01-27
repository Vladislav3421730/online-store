package com.example.webapp.service.impl;

import com.example.webapp.dto.ImageDto;
import com.example.webapp.exception.ImageNotFoundException;
import com.example.webapp.mapper.ImageMapper;
import com.example.webapp.mapper.ImageMapperImpl;
import com.example.webapp.model.Image;

import com.example.webapp.repository.ImageRepository;
import com.example.webapp.service.ImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ImageServiceImpl implements ImageService {

    ImageRepository imageRepository;
    ImageMapper imageMapper;

    @Override
    public ImageDto findById(Long id) {
        Image image = imageRepository.findById(id).orElseThrow(() ->
                new ImageNotFoundException("Image with id " + id + " was not found"));
        return imageMapper.toDTO(image);
    }
}
