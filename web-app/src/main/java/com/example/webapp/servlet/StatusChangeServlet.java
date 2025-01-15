package com.example.webapp.servlet;

import com.example.webapp.dto.OrderDto;
import com.example.webapp.model.Order;
import com.example.webapp.model.enums.Status;
import com.example.webapp.service.OrderService;
import com.example.webapp.service.impl.OrderServiceImpl;
import com.example.webapp.utils.JspHelper;
import com.example.webapp.utils.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/manager/status/change")
public class StatusChangeServlet extends HttpServlet {

    private final OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String status = req.getParameter("status");
        long statusId = Validator.validateLong(req.getParameter("id"));

        OrderDto order = orderService.findById(statusId);
        order.setStatus(status);

        orderService.update(order);
        req.setAttribute("orderDto", order);
        req.getRequestDispatcher(JspHelper.getPath("managerOrder")).forward(req, resp);
    }
}
