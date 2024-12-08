package com.example.webapp.repository.impl;

import com.example.webapp.model.Order;
import com.example.webapp.model.Product;
import com.example.webapp.repository.OrderRepository;
import com.example.webapp.utils.HibernateUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderRepositoryImpl implements OrderRepository {

    private final static OrderRepositoryImpl INSTANCE = new OrderRepositoryImpl();
    public static OrderRepositoryImpl getInstance(){
        return INSTANCE;
    }

    @Override
    public List<Order> findAll() {
        return HibernateUtils.getSessionFactory().openSession()
                .createQuery("from Order o order by o.createdAt desc ", Order.class)
                .getResultList();
    }

    @Override
    public Optional<Order> findById(Long id) {
        Order order = HibernateUtils.getSessionFactory().openSession().
                get(Order.class, id);
        return Optional.of(order);
    }
}
