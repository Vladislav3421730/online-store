package com.example.webapp.dao;

import com.example.webapp.model.User;
import com.example.webapp.model.enums.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao implements DAO<Long, User> {

    private final static SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();

    private final static UserDao INSTANCE=new UserDao();
    public static UserDao getInstance(){
        return INSTANCE;
    }

    @Override
    public List<User> findAll() {
        Session session= sessionFactory.openSession();
        session.beginTransaction();
        List<User> users = session.createQuery(
                        "SELECT u FROM User u WHERE :adminRole not in elements(u.roleSet) order by u.id", User.class)
                .setMaxResults(50)
                .setParameter("adminRole", Role.ROLE_ADMIN)
                .getResultList();
        session.getTransaction().commit();
        return users;
    }

    @Override
    public Optional<User> findById(Long id) {
        Session session= sessionFactory.openSession();
        session.beginTransaction();
        User user=session.createQuery("FROM User u WHERE u.id = :id", User.class)
                .setParameter("id",id)
                .uniqueResult();
        session.getTransaction().commit();
        return Optional.ofNullable(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        Session session=sessionFactory.getCurrentSession();
        session.beginTransaction();
        User updatedUser = (User) session.merge(user);
        session.getTransaction().commit();
        return updatedUser;
    }

    @Override
    @Transactional
    public void delete(Long id) {

    }

    @Override
    @Transactional
    public void save(User user) {
        Session session= sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }

    public Optional<User> findByEmail(String email){
        Session session= sessionFactory.openSession();
        session.beginTransaction();
        User user=session.createQuery("FROM User u WHERE u.email = :email", User.class)
                .setParameter("email",email).uniqueResult();
        session.getTransaction().commit();
        return Optional.ofNullable(user);
    }
}
