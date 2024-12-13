package com.example.webapp.servlet;

import com.example.webapp.service.OrderService;
import com.example.webapp.service.impl.OrderServiceImpl;
import com.example.webapp.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebServlet("/manager/order/search")
@Slf4j
public class ManagerOrderSearchServlet extends HttpServlet {

    private final OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String emailParam = req.getParameter("search");
        log.info("Trying to find user with email {}",emailParam);
        if(emailParam.isBlank()){
            log.info("email is empty");
            resp.sendRedirect(req.getContextPath() +"/manager/orders");
            return;
        }
        req.setAttribute("orders",orderService.findAllByUserEmail(emailParam));
        req.getRequestDispatcher(JspHelper.getPath("managerOrders")).forward(req,resp);
    }
}
