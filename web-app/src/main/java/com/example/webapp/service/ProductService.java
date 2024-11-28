package com.example.webapp.service;

import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.model.Product;


import java.util.List;

public interface ProductService {

    void save(Product product);
    List<Product> findAll();
    Product findById(Long id);
    List<Product> findAllBySearch(String word);
    List<Product> findAllByFilter(ProductFilterDTO productFilterDTO);

}
