package com.example.webapp.service;


import com.example.webapp.dao.UserDao;
import com.example.webapp.dto.RegisterUserDto;
import com.example.webapp.model.Order;
import com.example.webapp.model.Product;
import com.example.webapp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(RegisterUserDto registerUserDto);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String number);
    User findById(Long id);
    void update(User user);
    List<User> findAll();
    void addProductToCart(User user, Product product);
    void makeOrder(User user, Order order);

}
