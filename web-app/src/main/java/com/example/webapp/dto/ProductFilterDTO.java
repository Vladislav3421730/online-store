package com.example.webapp.dto;

public record ProductFilterDTO(
        String sort,
        String category,
        double minPrice,
        double maxPrice) {
}
