package com.example.webapp.service.impl;


import com.example.webapp.exception.ProductNotFoundException;
import com.example.webapp.repository.impl.ProductRepositoryImpl;
import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.model.Product;
import com.example.webapp.service.ProductService;
import com.example.webapp.utils.HibernateValidatorUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.transaction.Transactional;
import javax.validation.*;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductServiceImpl implements ProductService {

    private static final ProductServiceImpl INSTANCE = new ProductServiceImpl();

    public static ProductServiceImpl getInstance() {
        return INSTANCE;
    }

    private final ProductRepositoryImpl productRepository = ProductRepositoryImpl.getInstance();

    @Override
    @Transactional
    public void save(@Valid Product product) {
        Set<ConstraintViolation<Product>> violations = HibernateValidatorUtil.getValidator().validate(product);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(()->
                new ProductNotFoundException("Product with id "+id+" was not found"));
    }

    @Override
    public List<Product> findAllBySearch(String title) {
        return productRepository.findAllByTitle(title);
    }

    @Override
    public List<Product> findAllByFilter(ProductFilterDTO productFilterDTO) {
        return productRepository.findAllByFilter(productFilterDTO);
    }

    @Override
    public Product update(@Valid Product product) {
        Set<ConstraintViolation<Product>> violations = HibernateValidatorUtil.getValidator().validate(product);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return productRepository.update(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.delete(id);
    }


}
