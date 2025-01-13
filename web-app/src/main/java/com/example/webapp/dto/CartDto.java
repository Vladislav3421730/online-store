package com.example.webapp.dto;

import lombok.Data;


@Data
public class CartDto {

    private Long id;
    private int amount;
    private ProductDto product;
}
