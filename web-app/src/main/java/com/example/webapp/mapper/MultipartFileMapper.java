package com.example.webapp.mapper;


import com.example.webapp.dto.CreateImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class MultipartFileMapper {

    public static CreateImageDto map(MultipartFile file) {
        try {
            return CreateImageDto.builder()
                    .contentType(file.getContentType())
                    .bytes(file.getBytes())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
