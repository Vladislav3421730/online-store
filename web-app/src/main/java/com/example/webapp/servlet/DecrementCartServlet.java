package com.example.webapp.servlet;

import com.example.webapp.model.Cart;
import com.example.webapp.model.User;
import com.example.webapp.service.CartService;
import com.example.webapp.service.UserService;
import com.example.webapp.service.impl.CartServiceImpl;
import com.example.webapp.service.impl.UserServiceImpl;
import com.example.webapp.utils.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/cart/decrement")
@Slf4j
public class DecrementCartServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();
    private final CartService cartService = CartServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        int index = Validator.validateInt(req.getParameter("index"));

        List<Cart> userCarts = user.getCarts();
        cartService.decrementAmountOfCartInBasket(userCarts,index);

        req.getSession().setAttribute("user",userService.update(user));
        resp.sendRedirect(req.getContextPath()+"/user/cart");
    }

}
