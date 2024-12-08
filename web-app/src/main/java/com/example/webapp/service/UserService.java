package com.example.webapp.service;


import com.example.webapp.model.Order;
import com.example.webapp.model.Product;
import com.example.webapp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String number);
    User findById(Long id);
    User update(User user);
    List<User> findAll();
    User addProductToCart(User user, Product product);
    void bun(User user);
    User makeOrder(User user, Order order);

}
