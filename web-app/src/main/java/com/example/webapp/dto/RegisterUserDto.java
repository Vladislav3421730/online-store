package com.example.webapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserDto {

    private String username;
    private String password;
    private String email;
    private String phoneNumber;
}
