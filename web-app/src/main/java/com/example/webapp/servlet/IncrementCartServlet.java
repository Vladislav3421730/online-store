package com.example.webapp.servlet;

import com.example.webapp.model.Cart;
import com.example.webapp.model.User;
import com.example.webapp.service.UserService;
import com.example.webapp.service.impl.UserServiceImpl;
import com.example.webapp.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/cart/increment")
@Slf4j
public class IncrementCartServlet extends HttpServlet {

    private final UserService userService= UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        int index;
        try {
            log.info("Received request to update cart. Query parameter: index={}", req.getParameter("index"));
            index = Integer.parseInt(req.getParameter("index"));
        } catch (NumberFormatException e) {
            log.error("Invalid cart index parameter: {}", req.getParameter("index"), e);
            req.setAttribute("error", "Ошибка: неверный индекс товара в корзине.");
            req.getRequestDispatcher(JspHelper.getPath("cart")).forward(req, resp);
            return;
        }
        List<Cart> userCarts = user.getCarts();
        Cart cart = userCarts.get(index);
        if (cart.getAmount() + 1 > cart.getProduct().getAmount()) {
            log.error("The quantity of goods in the basket is equal to the quantity in the warehouse," +
                    " it is impossible to increase the quantity of goods {}",cart.getProduct().getAmount());
            req.setAttribute("error", "Ошибка, вы не можете указать количество товара большее, чем есть на складе");
            req.getRequestDispatcher(JspHelper.getPath("cart")).forward(req, resp);
            return;
        }
        cart.setAmount(cart.getAmount() + 1);
        userCarts.set(index, cart);
        log.info("The quantity of the product {} has been increased by 1 " +
                "{}",cart.getProduct().getTitle(),cart.getAmount());
        req.getSession().setAttribute("user",userService.update(user));
        resp.sendRedirect(req.getContextPath()+"/user/cart");
    }
}
