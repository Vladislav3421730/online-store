package com.example.webapp.dao;

import com.example.webapp.model.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDao implements DAO<Long, Product> {

    private final static SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();

    private static final ProductDao INSTANCE=new ProductDao();
    public static ProductDao getInstance(){
        return INSTANCE;
    }

    @Override
    public List<Product> findAll() {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        List<Product> users=session.createQuery("from Product p",Product.class).getResultList();
        session.getTransaction().commit();
        return users;
    }

    @Override
    public Optional<Product> findById(Long id) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        Product product=session.get(Product.class,id);
        session.getTransaction().commit();
        return Optional.ofNullable(product);
    }

    @Override
    public void update(Product product) {
        Session session=sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(product);
        session.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Product product = session.get(Product.class, id);
        if (product != null) {
            Product mergedProduct = (Product) session.merge(product);
            session.delete(mergedProduct);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void save(Product product) {
        Session session= sessionFactory.openSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
    }
}
