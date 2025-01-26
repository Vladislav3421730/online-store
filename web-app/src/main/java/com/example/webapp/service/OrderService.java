package com.example.webapp.service;

import com.example.webapp.dto.OrderDto;
import com.example.webapp.model.Order;
import com.example.webapp.model.Product;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<OrderDto> findAll();

    OrderDto findById(Long id);

    List<OrderDto> findAllByUserEmail(String email);

    List<OrderDto> findAllByUserId(Long id);

    void update(OrderDto order);
}
