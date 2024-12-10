package com.example.webapp.service;

import com.example.webapp.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAll();
    Optional<Order> findById(Long id);
    List<Order> findAllByUserEmail(String email);
}
