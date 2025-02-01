package com.example.webapp.service;

import com.example.webapp.dto.CreateProductDto;
import com.example.webapp.dto.ProductDto;
import com.example.webapp.dto.ProductFilterDTO;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Optional;

public interface ProductService {

    void save(CreateProductDto createProductDTO, List<MultipartFile> images);

    List<ProductDto> findAll();

    ProductDto findById(Long id);

    Optional<ProductDto> findByIdAsOptional(Long id);

    List<ProductDto> findAllByTitle(String word);

    List<ProductDto> findAllByFilter(ProductFilterDTO productFilterDTO);

    List<ProductDto> findAllByPriceFilter(ProductFilterDTO productFilterDTO, int initIndex);

    int getTotalAmount(ProductFilterDTO productFilterDTO);

    void update(ProductDto product);

    void delete(Long id);

}
