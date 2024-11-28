package com.example.webapp.dao;

import com.example.webapp.model.Image;
import com.example.webapp.model.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ImageDao {

    private final static SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();

    private static final ImageDao INSTANCE=new ImageDao();
    public static ImageDao getInstance(){
        return INSTANCE;
    }

    public Optional<Image> findById(Long id) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        Image image=session.get(Image.class,id);
        session.getTransaction().commit();
        return Optional.ofNullable(image);
    }

}
