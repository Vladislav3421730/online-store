package com.example.webapp.service.impl;


import com.example.webapp.dto.CreateProductDto;
import com.example.webapp.dto.ProductDto;
import com.example.webapp.exception.ProductNotFoundException;
import com.example.webapp.mapper.ProductMapper;
import com.example.webapp.mapper.ProductMapperImpl;
import com.example.webapp.model.Image;
import com.example.webapp.dao.impl.ImageDaoImpl;
import com.example.webapp.dao.impl.ProductDaoImpl;
import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.model.Product;
import com.example.webapp.service.ProductService;
import com.example.webapp.utils.HibernateValidatorUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import javax.validation.*;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ProductServiceImpl implements ProductService {

    private static final ProductServiceImpl INSTANCE = new ProductServiceImpl();

    public static ProductServiceImpl getInstance() {
        return INSTANCE;
    }

    private final ProductDaoImpl productRepository = ProductDaoImpl.getInstance();
    private final ImageDaoImpl imageRepository = ImageDaoImpl.getInstance();
    private final ProductMapper productMapper = new ProductMapperImpl();

    @Override
    @Transactional
    public void save(CreateProductDto createProductDTO) {
        Product product = productMapper.toNewEntity(createProductDTO);
        Set<ConstraintViolation<Product>> violations = HibernateValidatorUtil.getValidator().validate(product);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        productRepository.save(product);
        for (Image image : product.getImageList()) {
            image.setProduct(product);
            imageRepository.save(image);
        }
    }

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()->
                new ProductNotFoundException("Product with id "+id+" was not found"));
        return productMapper.toDTO(product);
    }

    @Override
    public List<ProductDto> findAllBySearch(String title) {
        return productRepository.findAllByTitle(title).stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public List<ProductDto> findAllByFilter(ProductFilterDTO productFilterDTO) {
        return productRepository.findAllByFilter(productFilterDTO).stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public void update(@Valid Product product) {
        Set<ConstraintViolation<Product>> violations = HibernateValidatorUtil.getValidator().validate(product);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        productRepository.update(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.delete(id);
    }


}
