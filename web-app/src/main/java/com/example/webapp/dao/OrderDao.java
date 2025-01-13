package com.example.webapp.dao;

import com.example.webapp.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    List<Order> findAllByUserEmail(String email);
    Optional<Order> findById(Long id);

}
