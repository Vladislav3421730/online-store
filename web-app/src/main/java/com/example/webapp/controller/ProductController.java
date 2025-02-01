package com.example.webapp.controller;

import com.example.webapp.dto.*;
import com.example.webapp.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@Slf4j
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductController {

    ProductService productService;

    @GetMapping
    public String findAllProducts(Model model) {
        List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/{id}")
    public String findProductById(@PathVariable Long id, Model model) {
        ProductDto product = productService.findById(id);
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/filter")
    public String filterProducts(ProductFilterDTO productFilterDTO, Model model) {
        log.info("ProductFilterDTO: {}", productFilterDTO);
        List<ProductDto> products = productService.findAllByFilter(productFilterDTO);
        model.addAttribute("products", products);
        model.addAttribute("search", productFilterDTO.getTitle());
        return "index";
    }

    @GetMapping("/search")
    public String searchProducts(
            @RequestParam(value = "search", required = false) String searchParameter,
            Model model) {
        List<ProductDto> products = productService.findAllByTitle(searchParameter);
        model.addAttribute("products", products);
        model.addAttribute("search", searchParameter);
        return "index";
    }

}
