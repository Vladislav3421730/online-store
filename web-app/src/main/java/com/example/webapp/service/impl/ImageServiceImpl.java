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


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageServiceImpl implements ImageService {

    private final static ImageServiceImpl INSTANCE = new ImageServiceImpl();
    public static ImageServiceImpl getInstance(){
        return INSTANCE;
    }

    private final ImageDaoImpl imageRepository = ImageDaoImpl.getInstance();
    private final ImageMapper imageMapper = new ImageMapperImpl();

    @Override
    public ImageDto findById(Long id) {
        Image image = imageRepository.findById(id).orElseThrow(()->
                new  ImageNotFoundException("Image with id "+id+" was not found"));
        return imageMapper.toDTO(image);
    }
}
