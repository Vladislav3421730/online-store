package com.example.webapp.service.impl;

import com.example.webapp.exception.ImageNotFoundException;
import com.example.webapp.model.Image;
import com.example.webapp.repository.ImageRepository;
import com.example.webapp.repository.impl.ImageRepositoryImpl;
import com.example.webapp.service.ImageService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageServiceImpl implements ImageService {

    private final static ImageServiceImpl INSTANCE = new ImageServiceImpl();
    public static ImageServiceImpl getInstance(){
        return INSTANCE;
    }

    private final ImageRepository imageRepository = ImageRepositoryImpl.getInstance();

    @Override
    public Image findById(Long id) {
        return imageRepository.findById(id).orElseThrow(()->
                new  ImageNotFoundException("Image with id "+id+" was not found"));
    }
}
