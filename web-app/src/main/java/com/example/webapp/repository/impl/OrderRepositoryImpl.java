package com.example.webapp.repository.impl;

import com.example.webapp.model.Order;
import com.example.webapp.repository.OrderRepository;
import com.example.webapp.utils.HibernateUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import javax.transaction.Transactional;
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
    public List<Order> findAllByUserEmail(String email) {
        return HibernateUtils.getSessionFactory().openSession()
                .createQuery("from Order o where lower(o.user.email)=:email" +
                        " order by o.createdAt desc ", Order.class)
                .setParameter("email",email.toLowerCase())
                .getResultList();
    }

    @Override
    public Optional<Order> findById(Long id) {
        Order order = HibernateUtils.getSessionFactory().openSession().
                get(Order.class, id);
        return Optional.ofNullable(order);
    }

    @Override
    @Transactional
    public void update(Order order) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(order);
        session.getTransaction().commit();
    }
}
