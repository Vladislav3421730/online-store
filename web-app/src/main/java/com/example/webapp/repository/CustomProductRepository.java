package com.example.webapp.repository;

import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.model.Product;

import java.util.List;


public interface CustomProductRepository {
    List<Product> findAllByFilter(ProductFilterDTO productFilterDTO);

    List<Product> findAllByPriceFilter(ProductFilterDTO productFilterDTO, int initIndex);

    void deleteProductWithOrderItems(Long productId);

    int getTotalAmountByFilter(ProductFilterDTO productFilterDTO);
}
