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
    public void save(User entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
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

    @Override
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

    @Override
    public void addRoleManagerToUser(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("INSERT INTO user_role (user_id, roleset) VALUES (:userId, :role)")
                    .setParameter("userId", userId)
                    .setParameter("role", Role.ROLE_MANAGER.name())
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeRoleManagerFromUser(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("DELETE FROM user_role where user_id=:userId and roleset=:role")
                    .setParameter("userId", userId)
                    .setParameter("role", Role.ROLE_MANAGER.name())
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void bunUser(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("UPDATE User u SET u.isBun = CASE " +
                            "WHEN u.isBun = true THEN false ELSE true end where u.id=:userId")
                    .setParameter("userId",userId)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }
}
