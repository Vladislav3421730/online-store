package com.example.webapp.service.impl;

import com.example.webapp.dto.CreateImageDto;
import com.example.webapp.dto.CreateProductDto;
import com.example.webapp.dto.ProductDto;
import com.example.webapp.exception.ProductNotFoundException;
import com.example.webapp.mapper.ImageMapper;
import com.example.webapp.mapper.ImageMapperImpl;
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
import java.util.ArrayList;
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
    ProductMapper productMapper = new ProductMapperImpl();
    ImageMapper imageMapper = new ImageMapperImpl();

    @Override
    public void save(CreateProductDto createProductDTO, List<CreateImageDto> imageDtos) {
        log.info("Saving new product: {}", createProductDTO.getTitle());

        Product product = productMapper.toNewEntity(createProductDTO);
        product.setImageList(new ArrayList<>());
        for (CreateImageDto imageDto : imageDtos) {
            Image image = imageMapper.toNewEntity(imageDto);
            product.addImageToList(image);
        }

        Set<ConstraintViolation<Product>> violations = HibernateValidatorUtil.getValidator().validate(product);
        if (!violations.isEmpty()) {
            log.warn("Validation failed for product: {}", createProductDTO.getTitle());
            throw new ConstraintViolationException(violations);
        }

        productDao.save(product);
        log.info("Product saved successfully: {}", createProductDTO.getTitle());
    }

    @Override
    public List<ProductDto> findAll() {
        log.info("Fetching all products");
        return productDao.findAll().stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public ProductDto findById(Long id) {
        log.info("Fetching product with id: {}", id);
        Product product = productDao.findById(id).orElseThrow(() -> {
            log.error("Product with id {} not found", id);
            return new ProductNotFoundException("Product with id " + id + " was not found");
        });
        return productMapper.toDTO(product);
    }

    @Override
    public Optional<ProductDto> findByIdAsOptional(Long id) {
        log.info("Fetching product with id: {}", id);
        Optional<Product> product = productDao.findById(id);
        return product.map(productMapper::toDTO);
    }

    @Override
    public List<ProductDto> findAllBySearch(String title) {
        log.info("Searching products with title: {}", title);
        return productDao.findAllByTitle(title).stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public List<ProductDto> findAllByFilter(ProductFilterDTO productFilterDTO) {
        log.info("Fetching products with filter: {}", productFilterDTO);
        return productDao.findAllByFilter(productFilterDTO).stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public List<ProductDto> findAllByPriceFilter(ProductFilterDTO productFilterDTO, int initIndex) {
        log.info("Fetching products with price filter: {} and page: {}", productFilterDTO, initIndex);
        return productDao.findAllByPriceFilter(productFilterDTO, (initIndex - 1) * 10).stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    public int getTotalAmount(ProductFilterDTO productFilterDTO) {
        log.info("Fetching total amount of products with filter: {}", productFilterDTO);
        return productDao.getTotalAmountByFilter(productFilterDTO);
    }

    @Override
    public void update(ProductDto productDto) {
        log.info("Updating product with id: {}", productDto.getId());

        Product product = productMapper.toEntity(productDto);
        Set<ConstraintViolation<Product>> violations = HibernateValidatorUtil.getValidator().validate(product);
        if (!violations.isEmpty()) {
            log.warn("Validation failed for product update: {}", productDto.getId());
            throw new ConstraintViolationException(violations);
        }

        productDao.update(product);
        log.info("Product updated successfully with id: {}", productDto.getId());
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting product with id: {}", id);
        productDao.delete(id);
        log.info("Product with id {} deleted successfully", id);
    }

}
