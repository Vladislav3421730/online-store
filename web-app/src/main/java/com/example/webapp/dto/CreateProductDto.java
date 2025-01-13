package com.example.webapp.dto;


import lombok.Builder;
import lombok.Data;


import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CreateProductDto {
    private String title;
    private String description;
    private String category;
    private int amount;
    private BigDecimal coast;
    private List<CreateImageDto> imageList;


}
