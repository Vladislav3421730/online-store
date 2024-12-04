package com.example.webapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class ProductFilterDTO{
    private String sort;
    private String category;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String title;
}
