package com.example.webapp.dto;

import com.example.webapp.model.Image;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {

    private Long id;
    private String title;
    private String description;
    private String category;
    private int amount;
    private BigDecimal coast;
    private List<ImageDto> imageList;

    public void addImageToList(ImageDto image) {
        imageList.add(image);
        image.setProductId(this.id);
    }
}
