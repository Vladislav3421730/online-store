package com.example.webapp.service.impl;


import com.example.webapp.dao.DAO;
import com.example.webapp.dao.ProductDao;
import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.model.Product;
import com.example.webapp.service.ProductService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

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
    @Override
    public List<Product> findAll(){
        return productDAO.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productDAO.findById(id).orElse(null);
    }

    @Override
    public List<Product> findAllBySearch(String word) {
        return findAll().stream()
                .filter(x->x.getTitle().toLowerCase().contains(word.toLowerCase()))
                .toList();
    }

    @Override
    public List<Product> findAllByFilter(ProductFilterDTO productFilterDTO) {
    return null;
    }


}
