package com.example.webapp.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateImageDto {
    private String contentType;
    private byte[] bytes;

}
