package com.example.webapp.controller;

import com.example.webapp.dto.AddressDto;
import com.example.webapp.dto.OrderDto;
import com.example.webapp.dto.ProductDto;
import com.example.webapp.dto.UserDto;
import com.example.webapp.service.*;
import com.example.webapp.utils.UserHelperUtils;
import com.example.webapp.utils.Messages;
import com.example.webapp.utils.OrderPayingValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {

    SecurityContextService securityContextService;
    ProductService productService;
    UserService userService;
    CartService cartService;

    @GetMapping("/account")
    public String getAccountPage(Model model) {
        UserDto userDto = securityContextService.getUser();
        userDto.getOrders().sort(Comparator.comparing(OrderDto::getCreatedAt).reversed());
        model.addAttribute("user", userDto);
        return "account";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model) {
        UserDto user = securityContextService.getUser();
        model.addAttribute("user", user);
        if (!user.getCarts().isEmpty()) {
            BigDecimal totalCoast = UserHelperUtils.getCoast(user);
            log.info("The sum of the prices of all items in the basket {}", totalCoast);
            model.addAttribute("totalCoast", totalCoast);
        }
        List<AddressDto> addresses = UserHelperUtils.updateAddresses(user);
        model.addAttribute("addresses", addresses);
        return "cart";
    }

    @PostMapping("/cart")
    public String madeOrder(
            @ModelAttribute AddressDto addressDto,
            @RequestParam("totalCoast") Double totalCoast,
            Model model) {
        log.info("address: {}", addressDto);
        UserDto user = securityContextService.getUser();
        BigDecimal totalPrice = BigDecimal.valueOf(totalCoast);
        if (!OrderPayingValidator.validateOrderCoast(totalPrice)) {
            model.addAttribute("error", Messages.FAILED_PAYMENT_MESSAGE);
            model.addAttribute("totalCoast", totalPrice);
            return getCartPage(model);
        }
        OrderDto order = OrderDto.builder()
                .totalPrice(totalPrice)
                .address(addressDto)
                .build();
        UserDto updatedUser = userService.makeOrder(user, order);
        securityContextService.updateContext(updatedUser);
        model.addAttribute("success", Messages.SUCCESS_MESSAGE);
        model.addAttribute("user", securityContextService.getUser());
        return "cart";
    }

    @PostMapping("/cart/add/{id}")
    public String addProductToCart(@PathVariable Long id, Model model) {
        ProductDto productDto = productService.findById(id);
        if (productDto.getAmount() == 0) {
            log.info("Product's amount is 0");
            return "redirect:/products";
        }
        UserDto userDto = securityContextService.getUser();
        UserDto updatedUserDB = userService.addProductToCart(userDto, productDto);
        securityContextService.updateContext(updatedUserDB);
        return "redirect:/products";
    }

    @PostMapping("/cart/increment/{index}")
    public String incrementAmountOfProduct(
            @PathVariable Integer index,
            @RequestParam("totalCoast") Double totalCoast,
            Model model) {
        UserDto user = securityContextService.getUser();
        if (!cartService.incrementAmountOfCartInBasket(user.getCarts(), index)) {
            BigDecimal totalPrice = BigDecimal.valueOf(totalCoast);
            model.addAttribute("totalCoast", totalPrice);
            model.addAttribute("error", Messages.ERROR_MESSAGE_INCREMENT);
            return getCartPage(model);
        }
        securityContextService.updateContext(user);
        return "redirect:/user/cart";
    }

    @PostMapping("/cart/decrement/{index}")
    public String decrementAmountOfProduct(@PathVariable Integer index, Model model) {
        UserDto user = securityContextService.getUser();
        cartService.decrementAmountOfCartInBasket(user.getCarts(), index);
        securityContextService.updateContext(user);
        return "redirect:/user/cart";
    }

    @PostMapping("/cart/delete/{index}")
    public String deleteProductFromCart(@PathVariable Integer index, Model model) {
        UserDto user = securityContextService.getUser();
        cartService.deleteCartFromBasket(user.getCarts(), index);
        securityContextService.updateContext(user);
        return "redirect:/user/cart";
    }


}
