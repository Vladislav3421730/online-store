package com.example.webapp.servlet;

import com.example.webapp.model.Cart;
import com.example.webapp.model.User;
import com.example.webapp.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/user/cart")
@Slf4j
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (!user.getCarts().isEmpty()) {
            BigDecimal totalCoast = user.getCarts().stream()
                    .map(x -> x.getProduct().getCoast().multiply(BigDecimal.valueOf(x.getAmount())))
                    .reduce(BigDecimal::add).get();
            log.info("The sum of the prices of all items in the basket {}",totalCoast);
            req.setAttribute("totalCoast", totalCoast);
        }
        req.getRequestDispatcher(JspHelper.getPath("cart")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

    }
}
