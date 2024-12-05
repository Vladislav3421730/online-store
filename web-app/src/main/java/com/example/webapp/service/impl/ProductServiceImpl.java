package com.example.webapp.service.impl;


import com.example.webapp.dao.ProductDao;
import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.model.Product;
import com.example.webapp.service.ProductService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

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

    private final ProductDao productDAO = ProductDao.getInstance();
    private final ValidatorFactory factory = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator())
            .buildValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Override
    @Transactional
    public void save(@Valid Product product) {
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        productDAO.save(product);

    }

    @Override
    public List<Product> findAll() {
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

    @Override
    public Product update(@Valid Product product) {
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return productDAO.update(product);
    }


}
