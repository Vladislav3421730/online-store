package com.example.webapp.servlet.manager;

import com.example.webapp.dto.OrderDto;
import com.example.webapp.service.impl.OrderServiceImpl;
import com.example.webapp.service.OrderService;
import com.example.webapp.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/manager/orders")
public class ViewOrders extends HttpServlet {

    private final OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<OrderDto> orders = orderService.findAll();

        req.setAttribute("orders", orders);
        req.getRequestDispatcher(JspHelper.getPath("managerOrders")).forward(req, resp);
    }
}
