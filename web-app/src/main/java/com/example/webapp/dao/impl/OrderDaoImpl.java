package com.example.webapp.dao.impl;

import com.example.webapp.model.Order;
import com.example.webapp.dao.AbstractHibernateDao;
import com.example.webapp.dao.OrderDao;
import org.hibernate.Session;

import java.util.List;

public class OrderDaoImpl extends AbstractHibernateDao<Order> implements OrderDao {

    private final static OrderDaoImpl INSTANCE = new OrderDaoImpl();

    public static OrderDaoImpl getInstance(){
        return INSTANCE;
    }

    private OrderDaoImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> findAllByUserEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Order o where lower(o.user.email)=:email" +
                    " order by o.createdAt desc ", Order.class)
                    .setParameter("email",email.toLowerCase())
                    .getResultList();
        }
    }

}
