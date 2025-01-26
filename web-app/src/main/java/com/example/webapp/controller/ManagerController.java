package com.example.webapp.controller;

import com.example.webapp.dto.*;
import com.example.webapp.exception.InvalidParamException;
import com.example.webapp.service.ProductService;
import com.example.webapp.utils.EditProductUtils;
import com.example.webapp.utils.Messages;
import com.example.webapp.utils.Validator;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
            @ModelAttribute ProductDto product,
            @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {
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
            Long productId = Validator.validateLong(id);
            Optional<ProductDto> product = productService.findByIdAsOptional(productId);
            if (product.isPresent()) {
                model.addAttribute("products", List.of(product.get()));
            } else {
                model.addAttribute("products", List.of());
            }
        } catch (InvalidParamException e) {
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
            @Valid @ModelAttribute CreateProductDto createProductDto,
            BindingResult bindingResult,
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            Model model) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            model.addAttribute("errors", errorMessages);
            return "addProduct";
        }
        productService.save(createProductDto, files);
        return "redirect:/manager/products";
    }

}
