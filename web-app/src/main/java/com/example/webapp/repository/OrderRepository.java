package com.example.webapp.repository;

import com.example.webapp.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    List<Order> findAll();
    Optional<Order> findById(Long id);

}
