package com.example.webapp.repository.impl;

import com.example.webapp.model.User;
import com.example.webapp.model.enums.Role;
import com.example.webapp.repository.UserRepository;
import com.example.webapp.utils.HibernateUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRepositoryImpl implements UserRepository {

    private final static UserRepositoryImpl INSTANCE = new UserRepositoryImpl();
    public static UserRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<User> findAll() {
        return HibernateUtils.getSessionFactory()
                .openSession()
                .createQuery(
                        "SELECT u FROM User u WHERE :adminRole not in elements(u.roleSet) order by u.id", User.class)
                .setMaxResults(50)
                .setParameter("adminRole", Role.ROLE_ADMIN)
                .getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = HibernateUtils.getSessionFactory()
                .openSession()
                .createQuery("FROM User u WHERE u.id = :id", User.class)
                .setParameter("id", id)
                .uniqueResult();
        return Optional.ofNullable(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        User updatedUser = (User) session.merge(user);
        session.getTransaction().commit();
        return updatedUser;
    }

    @Override
    @Transactional
    public void save(User user) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }

    public Optional<User> findByEmail(String email) {
        User user = HibernateUtils.getSessionFactory()
                .openSession()
                .createQuery("FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .uniqueResult();
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByPhoneNumber(String number) {
        User user = HibernateUtils.getSessionFactory()
                .openSession()
                .createQuery("FROM User u WHERE u.phoneNumber = :number", User.class)
                .setParameter("number", number)
                .uniqueResult();
        return Optional.of(user);
    }
}
