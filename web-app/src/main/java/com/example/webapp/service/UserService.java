package com.example.webapp.service;


import com.example.webapp.dto.OrderDto;
import com.example.webapp.dto.ProductDto;
import com.example.webapp.dto.RegisterUserDto;
import com.example.webapp.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(RegisterUserDto registerUserDto);

    Optional<UserDto> findByEmail(String email);

    Optional<UserDto> findByPhoneNumber(String number);

    UserDto findById(Long id);

    List<UserDto> findAll();

    UserDto addProductToCart(UserDto user, ProductDto product);

    UserDto makeOrder(UserDto user, OrderDto order);

}
