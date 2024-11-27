package com.example.webapp.dao;

import com.example.webapp.model.Image;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
        return Optional.empty();
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
