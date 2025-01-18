package com.example.webapp.dao;

import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.model.Product;


import java.util.List;

public interface ProductDao {

    List<Product> findAllByTitle(String title);

    List<Product> findAllByFilter(ProductFilterDTO productFilterDTO);

    List<Product> findAllByPriceFilter(ProductFilterDTO productFilterDTO, int initIndex);

    int getTotalAmountByFilter(ProductFilterDTO productFilterDTO);

    List<Product> findAllOffset(int initIndex);
}
