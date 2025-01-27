package com.example.webapp.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterDTO{
    private String sort;
    private String category;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String title;
}
