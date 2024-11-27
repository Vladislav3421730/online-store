package com.example.webapp.service.impl;


import com.example.webapp.dao.DAO;
import com.example.webapp.dao.ProductDao;
import com.example.webapp.model.Product;
import com.example.webapp.service.ProductService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductServiceImpl implements ProductService {

    private static final ProductServiceImpl INSTANCE=new ProductServiceImpl();
    public static ProductServiceImpl getInstance(){
        return INSTANCE;
    }

    private final DAO<Long,Product> productDAO= ProductDao.getInstance();


    @Override
    public void save(Product product) {
        productDAO.save(product);

    }


}
