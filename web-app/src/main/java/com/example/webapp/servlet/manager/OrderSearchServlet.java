package com.example.webapp.servlet.manager;

import com.example.webapp.dto.OrderDto;
import com.example.webapp.service.OrderService;
import com.example.webapp.service.impl.OrderServiceImpl;
import com.example.webapp.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@WebServlet("/manager/order/search")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class OrderSearchServlet extends HttpServlet {

    OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String emailParam = req.getParameter("search");
        log.info("Trying to find user with email {}",emailParam);
        if(emailParam.isBlank()){
            log.info("email is empty");
            resp.sendRedirect(req.getContextPath() +"/manager/orders");
            return;
        }
        List<OrderDto> orders = orderService.findAllByUserEmail(emailParam);
        req.setAttribute("orders",orders);
        req.getRequestDispatcher(JspHelper.getPath("managerOrders")).forward(req,resp);
    }
}
