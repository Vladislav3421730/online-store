package com.example.webapp.servlet;

import com.example.webapp.model.Order;
import com.example.webapp.model.User;
import com.example.webapp.service.UserService;
import com.example.webapp.service.impl.UserServiceImpl;
import com.example.webapp.utils.AddressFormationAssistant;
import com.example.webapp.utils.JspHelper;
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
        BigDecimal totalPrice = null;
        try {
            totalPrice = BigDecimal.valueOf(Double.parseDouble(req.getParameter("totalCoast")));
        } catch (NumberFormatException e) {
            log.error("Invalid totalPrice parameter: " + req.getParameter("totalCoast"), e);
            req.setAttribute("error", "Ошикбка, неверное значение суммы заказа");
            req.getRequestDispatcher(JspHelper.getPath("cart")).forward(req, resp);
            return;
        }
        Order order = new Order(totalPrice);
        order.setAddress(AddressFormationAssistant.formAddress(req));
        order.setOrderItems(user.getCarts().stream()
                        .map(x->OderItemCartMapper.map(x,order))
                        .toList());

        user.getCarts().clear();
        user.addOrderToList(order);
        req.getSession().setAttribute("user", userService.update(user));
        AddressFormationAssistant.updateAddresses(req);
        req.setAttribute("success", "Заказ был успешно оформлен");
        req.getRequestDispatcher(JspHelper.getPath("cart")).forward(req, resp);
    }


}
