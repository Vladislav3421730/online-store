package com.example.webapp.dao.impl;

import com.example.webapp.model.User;
import com.example.webapp.model.enums.Role;
import com.example.webapp.dao.AbstractHibernateDao;
import com.example.webapp.dao.UserDao;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends AbstractHibernateDao<User> implements UserDao {

    private final static UserDaoImpl INSTANCE = new UserDaoImpl();

    public static UserDaoImpl getInstance() {
        return INSTANCE;
    }

    private UserDaoImpl() {
        super(User.class);
    }

    @Override
    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT u FROM User u" +
                            " WHERE :adminRole not in elements(u.roleSet) order by u.id", User.class)
                    .setMaxResults(50)
                    .setParameter("adminRole", Role.ROLE_ADMIN)
                    .getResultList();
        }

    }

    @Override
    public Optional<User> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.createQuery("FROM User u WHERE u.id = :id", User.class)
                    .setParameter("id", id)
                    .uniqueResult();
            return Optional.ofNullable(user);
        }
    }

    public Optional<User> findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.createQuery("FROM User u WHERE  u.email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResult();
            return Optional.ofNullable(user);
        }
    }

    @Override
    public Optional<User> findByPhoneNumber(String number) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.createQuery("FROM User u WHERE u.phoneNumber = :number", User.class)
                    .setParameter("number", number)
                    .uniqueResult();
            return Optional.ofNullable(user);
        }
    }
}
