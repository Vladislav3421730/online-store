package com.example.webapp.dao.impl;

import com.example.webapp.dao.AbstractHibernateDao;
import com.example.webapp.dao.CartDao;
import com.example.webapp.model.Cart;
import org.hibernate.Session;

public class CartDaoImpl extends AbstractHibernateDao<Cart> implements CartDao {

    private static final CartDaoImpl INSTANCE = new CartDaoImpl();

    public static CartDaoImpl getInstance() {
        return INSTANCE;
    }

    private CartDaoImpl() {
        super(Cart.class);
    }

    public void incrementAmount(Long cartId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String INCREMENT_CART_SQL = "UPDATE Cart c SET c.amount = c.amount + 1 WHERE c.id = :cartId";
            session.createQuery(INCREMENT_CART_SQL)
                    .setParameter("cartId", cartId)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    public void decrementAmount(Long cartId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String DECREMENT_CART_SQL = "UPDATE Cart c SET c.amount = c.amount - 1 WHERE c.id = :cartId";
            session.createQuery(DECREMENT_CART_SQL)
                    .setParameter("cartId", cartId)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }
}
