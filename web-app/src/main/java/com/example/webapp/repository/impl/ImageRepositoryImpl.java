package com.example.webapp.repository.impl;

import com.example.webapp.model.Image;
import com.example.webapp.repository.ImageRepository;
import com.example.webapp.utils.HibernateUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageRepositoryImpl implements ImageRepository {

    private static final ImageRepositoryImpl INSTANCE=new ImageRepositoryImpl();
    public static ImageRepositoryImpl getInstance(){
        return INSTANCE;
    }

    @Override
    public Optional<Image> findById(Long id) {
        Session session= HibernateUtils.getSessionFactory().openSession();
        return Optional.ofNullable(session.get(Image.class,id));
    }

}
