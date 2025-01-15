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

        OrderDto orderDto = orderService.findById(orderId);
        UserDto user = userService.findById(orderDto.getUserId());

        req.setAttribute("orderDto", orderDto);
        req.setAttribute("user", user);
        req.getRequestDispatcher(JspHelper.getPath("managerOrder")).forward(req, resp);
    }
}
