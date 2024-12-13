package com.example.webapp.repository;

import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.model.Product;


import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    List<Product> findAllByTitle(String title);

    List<Product> findAllByFilter(ProductFilterDTO productFilterDTO);

    Optional<Product> findById(Long id);

    void update(Product product);

    void delete(Long id);

    void save(Product product);
}
