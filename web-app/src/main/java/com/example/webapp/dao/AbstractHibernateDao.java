package com.example.webapp.dao;

import com.example.webapp.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public abstract class AbstractHibernateDao<T> {

    private final Class<T> entityClass;
    protected final SessionFactory sessionFactory;

    protected AbstractHibernateDao(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.sessionFactory = HibernateUtils.getSessionFactory();
    }

    public List<T> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
        }
    }

    public Optional<T> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(entityClass, id));
        }
    }

    public void save(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
    }

    public void update(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
        }
    }

    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            T entity = session.get(entityClass, id);
            if (entity != null) {
                session.delete(entity);
            }
            session.getTransaction().commit();
        }
    }
}