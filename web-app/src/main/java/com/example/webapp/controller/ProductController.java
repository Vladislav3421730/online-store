package com.example.webapp.controller;

import com.example.webapp.dto.ProductDto;
import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.service.ProductService;
import com.example.webapp.service.impl.ProductServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

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
    public String filterProducts(
            @ModelAttribute ProductFilterDTO productFilterDTO,
            Model model) {
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

    @GetMapping("/manager")
    public String getProductsPage(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @ModelAttribute ProductFilterDTO productFilterDTO,
            Model model) {

        List<ProductDto> products = productService.findAllByPriceFilter(productFilterDTO, page);
        int totalProducts = productService.getTotalAmount(productFilterDTO);
        int totalPages = (int) Math.ceil((double) totalProducts / 10);

        model.addAttribute("filter", productFilterDTO);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "managerProducts";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(
            @PathVariable Long id,
            @RequestHeader(value = "Referer", defaultValue = "/manager/products") String referer) {
        productService.delete(id);
        return "redirect:" + referer;
    }

}
