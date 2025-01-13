package com.example.webapp.service.impl;

import com.example.webapp.exception.OrderNotFoundException;
import com.example.webapp.model.Order;
import com.example.webapp.dao.OrderDao;
import com.example.webapp.dao.impl.OrderDaoImpl;
import com.example.webapp.service.OrderService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderServiceImpl implements OrderService {

    private static final OrderServiceImpl INSTANCE = new OrderServiceImpl();

    public static OrderServiceImpl getInstance() {
        return INSTANCE;
    }

    private final OrderDaoImpl orderDao = OrderDaoImpl.getInstance();

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderDao.findById(id).orElseThrow(
                () -> new OrderNotFoundException("Order with id " + id + " not found"));
    }

    @Override
    public List<Order> findAllByUserEmail(String email) {
        return orderDao.findAllByUserEmail(email);
    }

    @Override
    public void update(Order order) {
        orderDao.update(order);
    }
}
