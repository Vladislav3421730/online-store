package com.example.webapp.mapper;

import com.example.webapp.dto.CartDto;
import com.example.webapp.model.Cart;

import com.example.webapp.model.Image;
import com.example.webapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;


@Mapper
public interface CartMapper {

    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "product",target = "product")
    CartDto cartToCartDto(Cart cart);

    default List<Long> mapImageListToIds(List<Image> images) {
        if (images == null) {
            return null;
        }
        return images.stream()
                .map(Image::getId)
                .collect(Collectors.toList());
    }
}
