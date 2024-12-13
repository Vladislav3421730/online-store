package com.example.webapp.service;

import com.example.webapp.model.Order;
import com.example.webapp.model.Product;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAll();
    Order findById(Long id);
    List<Order> findAllByUserEmail(String email);
    void update(Order order);
}
