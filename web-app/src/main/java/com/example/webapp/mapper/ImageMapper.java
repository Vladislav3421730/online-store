package com.example.webapp.mapper;

import com.example.webapp.dto.ImageDto;
import com.example.webapp.model.Image;
import org.mapstruct.Mapper;

@Mapper
public interface ImageMapper {

    ImageDto toDTO(Image image);

}
