package com.example.webapp.mapper;

import com.example.webapp.dto.ImageDto;
import com.example.webapp.model.Image;
import com.example.webapp.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ImageMapper {

    @Mapping(source = "product",target = "productId")
    ImageDto toDTO(Image image);

    @Mapping(source = "productId",target = "product")
    Image toEntity(ImageDto imageDto);

    default Long mapProductFromImageToImageDto(Product product){
        return product.getId();
    }

    default Product mapProductFromImageDtoToImage(Long value){
        return Product.builder().id(value).build();
    }


}
