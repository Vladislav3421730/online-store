package com.example.webapp.service.impl;

import com.example.webapp.dto.OrderDto;
import com.example.webapp.exception.OrderNotFoundException;
import com.example.webapp.mapper.OrderMapper;
import com.example.webapp.mapper.OrderMapperImpl;
import com.example.webapp.model.Order;
import com.example.webapp.repository.OrderRepository;
import com.example.webapp.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.tags.shaded.org.apache.bcel.generic.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    OrderMapper orderMapper = new OrderMapperImpl();

    @Override
    public List<OrderDto> findAll() {
        log.info("Fetching all orders");
        List<OrderDto> orders = orderRepository.findAllOrderByCreatedAt().stream()
                .map(orderMapper::toDTO)
                .toList();
        log.info("Retrieved {} orders", orders.size());
        return orders;
    }

    @Override
    public OrderDto findById(Long id) {
        log.info("Fetching order by ID: {}", id);
        Order order = orderRepository.findById(id).orElseThrow(() -> {
            log.error("Order with id {} not found", id);
            return new OrderNotFoundException("Order with id " + id + " not found");
        });
        log.info("Found order with ID: {}", id);
        return orderMapper.toDTO(order);
    }

    @Override
    public List<OrderDto> findAllByUserEmail(String email) {
        log.info("Fetching orders for user with email: {}", email);
        List<OrderDto> orders = orderRepository.findAllByUserEmail(email).stream()
                .map(orderMapper::toDTO)
                .toList();
        log.info("Retrieved {} orders for user with email: {}", orders.size(), email);
        return orders;
    }

    @Override
    public void update(OrderDto orderDto) {
        log.info("Updating order with ID: {}", orderDto.getId());
        Order order = orderMapper.toEntity(orderDto);
        orderRepository.save(order);
        log.info("Order with ID: {} updated successfully", orderDto.getId());
    }
}
