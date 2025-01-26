package com.example.webapp.controller;

import com.example.webapp.dto.OrderDto;
import com.example.webapp.dto.UserDto;
import com.example.webapp.service.OrderService;
import com.example.webapp.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderController {

    OrderService orderService;
    UserService userService;

    @GetMapping
    public String findAllOrders(Model model) {
        List<OrderDto> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/{id}")
    public String findOrderById(@PathVariable Long id, Model model) {
        OrderDto orderDto = orderService.findById(id);
        UserDto user = userService.findById(orderDto.getUserId());

        model.addAttribute("orderDto", orderDto);
        model.addAttribute("user", user);
        return "order";
    }

    @PostMapping("/{id}")
    public String updateOrderStatus(
            @RequestParam(name = "status") String status,
            @PathVariable Long id,
            Model model) {
        OrderDto order = orderService.findById(id);
        order.setStatus(status);
        orderService.update(order);

        UserDto user = userService.findById(order.getUserId());
        model.addAttribute("user", user);
        model.addAttribute("orderDto", order);
        return "order";
    }

    @GetMapping("/search")
    public String findAllOrdersByUserEmail(@RequestParam("search") String email, Model model) {
        if (email.isBlank()) {
            return "redirect:/orders";
        }
        List<OrderDto> orders = orderService.findAllByUserEmail(email);
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/user/{id}")
    public String findAllUserOrders(@PathVariable Long id, Model model) {
        List<OrderDto> orders = orderService.findAllByUserId(id);
        model.addAttribute("orders", orders);
        return "orders";
    }


}
