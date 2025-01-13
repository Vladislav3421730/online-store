package com.example.webapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageDto {
    private Long id;
    private String contentType;
    private byte[] bytes;
}
