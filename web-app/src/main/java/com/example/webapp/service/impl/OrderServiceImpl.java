package com.example.webapp.service.impl;

import com.example.webapp.model.Order;
import com.example.webapp.repository.OrderRepository;
import com.example.webapp.repository.impl.OrderRepositoryImpl;
import com.example.webapp.service.OrderService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderServiceImpl implements OrderService {

    private static final OrderServiceImpl INSTANCE = new OrderServiceImpl();
    public static OrderServiceImpl getInstance(){
        return INSTANCE;
    }

    private final OrderRepository orderRepository = OrderRepositoryImpl.getInstance();

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAllByUserEmail(String email) {
        return orderRepository.findAllByUserEmail(email);
    }
}
