package com.example.webapp.service.impl;


import com.example.webapp.dao.ProductDao;
import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.model.Product;
import com.example.webapp.service.ProductService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductServiceImpl implements ProductService {

    private static final ProductServiceImpl INSTANCE=new ProductServiceImpl();
    public static ProductServiceImpl getInstance(){
        return INSTANCE;
    }

    private final ProductDao productDAO= ProductDao.getInstance();


    @Override
    @Transactional
    public void save(Product product) {
        productDAO.save(product);

    }
    @Override
    public List<Product> findAll(){
        return productDAO.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productDAO.findById(id).orElse(null);
    }

    @Override
    public List<Product> findAllBySearch(String title) {
        return productDAO.findAllByTitle(title);
    }

    @Override
    public List<Product> findAllByFilter(ProductFilterDTO productFilterDTO) {
        return productDAO.findAllByFilter(productFilterDTO);

    }


}
