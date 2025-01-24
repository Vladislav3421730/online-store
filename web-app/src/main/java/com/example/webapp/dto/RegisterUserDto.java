package com.example.webapp.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@Builder
public class RegisterUserDto {

    @Size(min = 5, message = "Username must be at least 5 characters long.")
    @NotBlank(message = "Username cannot be blank.")
    private String username;

    @Size(min = 6, message = "Password must be at least 6 characters long.")
    @NotBlank(message = "Password cannot be blank.")
    private String password;

    @Email(message = "Email must be valid.")
    @NotBlank(message = "Email cannot be blank.")
    private String email;

    @Pattern(regexp = "^[+]375[0-9]{9}$", message = "Phone number must be in format +375XXXXXXXXX.")
    @NotBlank(message = "Phone number cannot be blank.")
    private String phoneNumber;
}
