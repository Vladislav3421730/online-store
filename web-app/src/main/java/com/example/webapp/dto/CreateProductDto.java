package com.example.webapp.dto;


import lombok.Builder;
import lombok.Data;


import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Builder
public class CreateProductDto {

    @NotBlank(message = "Title is required")
    @Size(min = 3, message = "Title's size must be more or equal than 3")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 10, message = "Description's size must be more or equal than 10")
    private String description;

    @NotBlank(message = "Category is required")
    @Size(min = 3, message = "Category's size must be more or equal than 3")
    private String category;

    @Min(value = 0, message = "Amount must be more or equal than 0")
    private Integer amount;

    @DecimalMin(value = "0.01", message = "Cost must be greater than or equal to 0.01")
    private BigDecimal coast;
}

