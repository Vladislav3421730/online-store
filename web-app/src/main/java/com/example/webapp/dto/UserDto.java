package com.example.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean bun;
    private String phoneNumber;
    private Set<String> roleSet;
    private List<CartDto> carts;
    private List<OrderDto> orders;

    public boolean isManager() {
        return roleSet.contains("ROLE_MANAGER");
    }

}
