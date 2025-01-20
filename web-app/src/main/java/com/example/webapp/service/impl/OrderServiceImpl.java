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
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderServiceImpl implements OrderService {

    private static final OrderServiceImpl INSTANCE = new OrderServiceImpl();

    public static OrderServiceImpl getInstance() {
        return INSTANCE;
    }

    OrderDaoImpl orderDao = OrderDaoImpl.getInstance();
    OrderMapper orderMapper = new OrderMapperImpl();

    @Override
    public List<OrderDto> findAll() {
        log.info("Fetching all orders");
        List<OrderDto> orders = orderDao.findAll().stream()
                .map(orderMapper::toDTO)
                .toList();
        log.info("Retrieved {} orders", orders.size());
        return orders;
    }

    @Override
    public OrderDto findById(Long id) {
        log.info("Fetching order by ID: {}", id);
        Order order = orderDao.findById(id).orElseThrow(() -> {
            log.error("Order with id {} not found", id);
            return new OrderNotFoundException("Order with id " + id + " not found");
        });
        log.info("Found order with ID: {}", id);
        return orderMapper.toDTO(order);
    }

    @Override
    public List<OrderDto> findAllByUserEmail(String email) {
        log.info("Fetching orders for user with email: {}", email);
        List<OrderDto> orders = orderDao.findAllByUserEmail(email).stream()
                .map(orderMapper::toDTO)
                .toList();
        log.info("Retrieved {} orders for user with email: {}", orders.size(), email);
        return orders;
    }

    @Override
    public void update(OrderDto orderDto) {
        log.info("Updating order with ID: {}", orderDto.getId());
        Order order = orderMapper.toEntity(orderDto);
        orderDao.update(order);
        log.info("Order with ID: {} updated successfully", orderDto.getId());
    }
}
