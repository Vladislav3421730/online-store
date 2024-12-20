package com.example.webapp.repository;

import com.example.webapp.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    List<Order> findAll();
    List<Order> findAllByUserEmail(String email);
    Optional<Order> findById(Long id);
    void update(Order order);



}
