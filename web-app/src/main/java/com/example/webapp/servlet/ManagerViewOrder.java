package com.example.webapp.servlet;

import com.example.webapp.dto.OrderDto;
import com.example.webapp.dto.UserDto;
import com.example.webapp.model.Order;
import com.example.webapp.service.OrderService;
import com.example.webapp.service.UserService;
import com.example.webapp.service.impl.OrderServiceImpl;
import com.example.webapp.service.impl.UserServiceImpl;
import com.example.webapp.utils.JspHelper;
import com.example.webapp.utils.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/manager/order")
public class ManagerViewOrder extends HttpServlet {

    private final OrderService orderService = OrderServiceImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long orderId = Validator.validateLong(req.getParameter("orderId"));
        OrderDto order = orderService.findById(orderId);
        System.out.println(order);
        UserDto user = userService.findById(order.getUserId());

        req.setAttribute("order", order);
        req.setAttribute("user",user);
        req.getRequestDispatcher(JspHelper.getPath("order")).forward(req, resp);
    }
}
