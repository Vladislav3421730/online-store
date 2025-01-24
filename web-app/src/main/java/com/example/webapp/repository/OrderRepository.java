package com.example.webapp.repository;

import com.example.webapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserEmail(String email);

    List<Order> findByOrderByCreatedAtDesc();
}
