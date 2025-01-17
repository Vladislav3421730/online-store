package com.example.webapp.service.impl;

import com.example.webapp.dto.OrderDto;
import com.example.webapp.exception.OrderNotFoundException;
import com.example.webapp.mapper.OrderMapper;
import com.example.webapp.mapper.OrderMapperImpl;
import com.example.webapp.model.Order;
import com.example.webapp.dao.OrderDao;
import com.example.webapp.dao.impl.OrderDaoImpl;
import com.example.webapp.service.OrderService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.transaction.Transactional;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {

    private static final OrderServiceImpl INSTANCE = new OrderServiceImpl();

    public static OrderServiceImpl getInstance() {
        return INSTANCE;
    }

    OrderDaoImpl orderDao = OrderDaoImpl.getInstance();
    OrderMapper orderMapper = new OrderMapperImpl();

    @Override
    public List<OrderDto> findAll() {
        return orderDao.findAll().stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    public OrderDto findById(Long id) {
        Order order = orderDao.findById(id).orElseThrow(
                () -> new OrderNotFoundException("Order with id " + id + " not found"));
        return orderMapper.toDTO(order);
    }

    @Override
    public List<OrderDto> findAllByUserEmail(String email) {
        return orderDao.findAllByUserEmail(email).stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public void update(OrderDto orderDto) {
        Order order = orderMapper.toEntity(orderDto);
        orderDao.update(order);
    }
}
