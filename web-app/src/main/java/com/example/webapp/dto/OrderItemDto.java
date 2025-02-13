package com.example.webapp.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long id;
    private int amount;
    private ProductDto product;
    private Long orderId;
}
