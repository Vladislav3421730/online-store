package com.example.webapp.dto;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private Long id;
    private int amount;
    private ProductDto product;
    private Long userId;
}
