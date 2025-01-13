package com.example.webapp.dao.impl;


import com.example.webapp.model.Image;
import com.example.webapp.dao.AbstractHibernateDao;
import com.example.webapp.dao.ImageDao;


public class ImageDaoImpl extends AbstractHibernateDao<Image> implements ImageDao {

    private static final ImageDaoImpl INSTANCE=new ImageDaoImpl();

    public static ImageDaoImpl getInstance(){
        return INSTANCE;
    }

    private ImageDaoImpl() {
        super(Image.class);
    }

}
