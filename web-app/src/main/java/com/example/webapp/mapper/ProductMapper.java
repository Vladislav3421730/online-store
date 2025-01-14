package com.example.webapp.mapper;

import com.example.webapp.dto.CreateImageDto;
import com.example.webapp.dto.CreateProductDto;
import com.example.webapp.dto.ProductDto;
import com.example.webapp.model.Image;
import com.example.webapp.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ProductMapper {

    @Mapping(source = "imageList", target = "imageList")
    Product toNewEntity(CreateProductDto dto);

    @Mapping(source = "imageList", target = "imageList")
    ProductDto toDTO(Product product);

    @Mapping(source = "imageList", target = "imageList")
    Product toEntity(ProductDto productDto);

    Image toEntity(CreateImageDto createImageDTO);
    List<Image> toImageList(List<CreateImageDto> createImageDtos);
    List<Image> toImageListFromProductDto(List<Long> values);
    Image toImageFromProductDto(Long value);

    default List<Long> mapImageListToIds(List<Image> images) {
        if (images == null) {
            return null;
        }
        return images.stream()
                .map(Image::getId)
                .collect(Collectors.toList());
    }
}