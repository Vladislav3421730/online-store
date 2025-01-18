package com.example.webapp.servlet.manager;

import com.example.webapp.dto.OrderDto;
import com.example.webapp.dto.UserDto;
import com.example.webapp.model.Order;
import com.example.webapp.model.enums.Status;
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
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebServlet("/manager/status/change")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class StatusChangeServlet extends HttpServlet {

    OrderService orderService = OrderServiceImpl.getInstance();
    UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String status = req.getParameter("status");
        long statusId = Validator.validateLong(req.getParameter("id"));
        log.info("{}",status);

        OrderDto order = orderService.findById(statusId);
        order.setStatus(status);

        orderService.update(order);
        UserDto user = userService.findById(order.getUserId());
        req.setAttribute("user",user);
        req.setAttribute("orderDto", order);
        req.getRequestDispatcher(JspHelper.getPath("managerOrder")).forward(req, resp);
    }
}
