package com.example.webapp.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean isBun;
    private String phoneNumber;
    private Set<String> roleSet;
    private List<CartDto> carts;
    private List<OrderDto> orders;

    public boolean isAdmin() {
        return roleSet.contains("ROLE_ADMIN");
    }

    public boolean isManager() {
        return roleSet.contains("ROLE_MANAGER");
    }

}
