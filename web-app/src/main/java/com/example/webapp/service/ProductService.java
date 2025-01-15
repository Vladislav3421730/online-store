package com.example.webapp.service;

import com.example.webapp.dto.CreateProductDto;
import com.example.webapp.dto.ProductDto;
import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.model.Product;


import java.util.List;

public interface ProductService {

    void save(CreateProductDto createProductDTO);
    List<ProductDto> findAll();
    ProductDto findById(Long id);
    List<ProductDto> findAllBySearch(String word);
    List<ProductDto> findAllByFilter(ProductFilterDTO productFilterDTO);
    void update(ProductDto product);
    void delete(Long id);

}
