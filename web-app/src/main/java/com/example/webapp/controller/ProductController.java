package com.example.webapp.controller;

import com.example.webapp.dto.ProductDto;
import com.example.webapp.dto.ProductFilterDTO;
import com.example.webapp.exception.InvalidParamException;
import com.example.webapp.model.Product;
import com.example.webapp.service.ProductService;
import com.example.webapp.service.impl.ProductServiceImpl;
import com.example.webapp.utils.Messages;
import com.example.webapp.utils.ResourceBundleUtils;
import com.example.webapp.utils.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

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

    @GetMapping("/manager/search")
    public String findProductByIdManagerPanel(
            @RequestParam(required = false) String id,
            @RequestParam(required = false, defaultValue = "ru") String lang,
            Model model, Locale locale) {
        if (id == null || id.isBlank()) {
            return "redirect:/manager/products";
        }
        try {
            Long productId = Validator.validateLong(id);
            Optional<ProductDto> product = productService.findByIdAsOptional(productId);
            if (product.isPresent()) {
                model.addAttribute("products", List.of(product.get()));
            } else {
                model.addAttribute("products", List.of());
            }
        } catch (InvalidParamException e) {
            model.addAttribute("products", List.of());
//            String errorMessage =  messageSource.getMessage(Messages.ERROR_MESSAGE, null, locale);
//            model.addAttribute("error",errorMessage);
        }
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
