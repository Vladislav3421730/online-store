package com.example.webapp.dao;

import com.example.webapp.model.Image;
import com.example.webapp.model.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ImageDao implements DAO<Long,Image> {

    private final static SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();

    private static final ImageDao INSTANCE=new ImageDao();
    public static ImageDao getInstance(){
        return INSTANCE;
    }

    @Override
    public List<Image> findAll() {
        return null;
    }

    @Override
    public Optional<Image> findById(Long id) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        Image image=session.get(Image.class,id);
        session.getTransaction().commit();
        return Optional.ofNullable(image);
    }

    @Override
    public void update(Image student) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void save(Image student) {

    }
}
