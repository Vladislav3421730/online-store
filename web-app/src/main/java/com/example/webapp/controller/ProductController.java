package com.example.webapp.controller;

import com.example.webapp.dto.*;
import com.example.webapp.exception.InvalidParamException;
import com.example.webapp.mapper.MultipartFileMapper;
import com.example.webapp.model.Product;
import com.example.webapp.service.ProductService;
import com.example.webapp.service.impl.ProductServiceImpl;
import com.example.webapp.utils.Messages;
import com.example.webapp.utils.ResourceBundleUtils;
import com.example.webapp.utils.Validator;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @GetMapping("/add")
    public String getAddingProductPage() {
        return "addProduct";
    }

    @PostMapping("/add")
    public String addNewProduct(
            @ModelAttribute CreateProductDto createProductDto,
            @RequestParam("files") List<MultipartFile> files) {

        List<CreateImageDto> images = files.stream()
                .map(MultipartFileMapper::map)
                .toList();
        productService.save(createProductDto, images);
        return "redirect:/products/manager";
    }

    @GetMapping("/{id}")
    public String findProductById(@PathVariable Long id, Model model) {
        ProductDto product = productService.findById(id);
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/filter")
    public String filterProducts(
            @Valid @ModelAttribute ProductFilterDTO productFilterDTO,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            model.addAttribute("errors", errorMessages);
            return "addProduct";
        }
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

        List<ProductDto> products = productService.findAllBySearch(searchParameter);
        model.addAttribute("products", products);
        model.addAttribute("search", searchParameter);

        return "index";
    }

}
