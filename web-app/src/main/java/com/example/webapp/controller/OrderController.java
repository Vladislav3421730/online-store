package com.example.webapp.controller;

import com.example.webapp.dto.OrderDto;
import com.example.webapp.dto.UserDto;
import com.example.webapp.service.OrderService;
import com.example.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

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
        model.addAttribute("user",user);
        model.addAttribute("orderDto", order);
        return "order";
    }


}
