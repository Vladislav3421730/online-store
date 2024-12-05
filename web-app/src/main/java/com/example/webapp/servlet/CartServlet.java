package com.example.webapp.servlet;

import com.example.webapp.model.Order;
import com.example.webapp.model.User;
import com.example.webapp.service.UserService;
import com.example.webapp.service.impl.UserServiceImpl;
import com.example.webapp.utils.AddressFormationAssistant;
import com.example.webapp.utils.JspHelper;
import com.example.webapp.validator.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import com.example.webapp.mapper.OderItemCartMapper;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/user/cart")
@Slf4j
public class CartServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (!user.getCarts().isEmpty()) {
            BigDecimal totalCoast = user.getCarts().stream()
                    .map(x -> x.getProduct().getCoast().multiply(BigDecimal.valueOf(x.getAmount())))
                    .reduce(BigDecimal::add).get();
            log.info("The sum of the prices of all items in the basket {}", totalCoast);
            req.setAttribute("totalCoast", totalCoast);
        }
        AddressFormationAssistant.updateAddresses(req);
        req.getRequestDispatcher(JspHelper.getPath("cart")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        BigDecimal totalPrice = BigDecimal.valueOf(Validator.validateDouble(req.getParameter("totalCoast")));
        Order order = new Order(totalPrice);
        order.setAddress(AddressFormationAssistant.formAddress(req));

        order.setOrderItems(user.getCarts().stream()
                .map(x -> OderItemCartMapper.map(x, order))
                .toList());

        user.getCarts().clear();
        user.addOrderToList(order);
        req.getSession().setAttribute("user", userService.update(user));
        req.setAttribute("success", "Заказ был успешно оформлен");
        req.getRequestDispatcher(JspHelper.getPath("cart")).forward(req, resp);
    }


}
