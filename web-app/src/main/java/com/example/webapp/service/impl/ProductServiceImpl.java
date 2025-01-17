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
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import javax.validation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductServiceImpl implements ProductService {

    private static final ProductServiceImpl INSTANCE = new ProductServiceImpl();

    public static ProductServiceImpl getInstance() {
        return INSTANCE;
    }

    ProductDaoImpl productDao = ProductDaoImpl.getInstance();
    ImageDaoImpl imageRepository = ImageDaoImpl.getInstance();
    ProductMapper productMapper = new ProductMapperImpl();

    @Override
    @Transactional
    public void save(CreateProductDto createProductDTO) {
        Product product = productMapper.toNewEntity(createProductDTO);
        Set<ConstraintViolation<Product>> violations = HibernateValidatorUtil.getValidator().validate(product);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        productDao.save(product);
        for (Image image : product.getImageList()) {
            image.setProduct(product);
            imageRepository.save(image);
        }
    }

    @Override
    public List<ProductDto> findAll() {
        return productDao.findAll().stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public ProductDto findById(Long id) {
        Product product = productDao.findById(id).orElseThrow(() ->
                new ProductNotFoundException("Product with id " + id + " was not found"));
        return productMapper.toDTO(product);
    }

    @Override
    public Optional<ProductDto> findByIdAsOptional(Long id) {
        Optional<Product> product = productDao.findById(id);
        return product.map(productMapper::toDTO);
    }

    @Override
    public List<ProductDto> findAllBySearch(String title) {
        return productDao.findAllByTitle(title).stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public List<ProductDto> findAllByFilter(ProductFilterDTO productFilterDTO) {
        return productDao.findAllByFilter(productFilterDTO).stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public List<ProductDto> findAllByPriceFilter(ProductFilterDTO productFilterDTO, int initIndex) {
        return productDao.findAllByPriceFilter(productFilterDTO, (initIndex - 1) * 10).stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public int getTotalAmount(ProductFilterDTO productFilterDTO) {
        return productDao.getTotalAmountByFilter(productFilterDTO);
    }

    @Override
    @Transactional
    public void update(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        Set<ConstraintViolation<Product>> violations = HibernateValidatorUtil.getValidator().validate(product);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        productDao.update(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productDao.delete(id);
    }

}
