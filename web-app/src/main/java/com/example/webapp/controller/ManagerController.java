package com.example.webapp.controller;

import com.example.webapp.dto.*;
import com.example.webapp.service.ProductService;
import com.example.webapp.utils.EditProductUtils;
import com.example.webapp.utils.Messages;
import com.example.webapp.utils.ValidatorUtils;
import jakarta.validation.ConstraintViolation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/manager/products")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ManagerController {

    ProductService productService;

    @GetMapping
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

    @GetMapping("/edit/{id}")
    public String getEditProductPage(@PathVariable Long id, Model model) {
        ProductDto product = productService.findById(id);
        model.addAttribute("product", product);
        return "productEdit";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(
            @PathVariable Long id,
            ProductDto product,
            @RequestParam(value = "files", required = false) List<MultipartFile> files, Model model) throws IOException {
        Set<ConstraintViolation<ProductDto>> violations = ValidatorUtils.getValidator().validate(product);
        if (!violations.isEmpty()) {
            List<String> errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .toList();
            model.addAttribute("errors", errorMessages);
            model.addAttribute("product", product);
            return "productEdit";
        }
        ProductDto existingProduct = productService.findById(id);
        EditProductUtils.editProduct(product, existingProduct, files);
        productService.update(existingProduct);
        return "redirect:/manager/products";
    }

    @GetMapping("/search")
    public String findProductByIdManagerPanel(
            @RequestParam(required = false) String id,
            Model model) {
        if (id == null || id.isBlank()) {
            return "redirect:/manager/products";
        }
        try {
            Long productId = Long.parseLong(id);
            Optional<ProductDto> product = productService.findByIdAsOptional(productId);
            if (product.isPresent()) {
                model.addAttribute("products", List.of(product.get()));
            } else {
                model.addAttribute("products", List.of());
            }
        } catch (NumberFormatException e) {
            model.addAttribute("products", List.of());
            model.addAttribute("error", Messages.ERROR_MESSAGE);
        }
        return "managerProducts";
    }

    @GetMapping("/add")
    public String getAddProductPage() {
        return "addProduct";
    }

    @PostMapping("/add")
    public String addNewProduct(
            CreateProductDto createProductDto,
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            Model model) {
        log.info("create product {}", createProductDto);
        Set<ConstraintViolation<CreateProductDto>> violations = ValidatorUtils.getValidator().validate(createProductDto);
        if (!violations.isEmpty()) {
            List<String> errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .toList();
            model.addAttribute("product", createProductDto);
            model.addAttribute("errors", errorMessages);
            return "addProduct";
        }
        productService.save(createProductDto, files);
        return "redirect:/manager/products";
    }

}
