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

@WebServlet("/user/cart/decrement")
@Slf4j
public class DecrementCartServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        int index = Integer.parseInt(req.getParameter("index"));
        List<Cart> userCarts = user.getCarts();
        Cart cart = userCarts.get(index);
        if (cart.getAmount() == 1) {
            userCarts.remove(index);
            log.info("The product has been removed from the user's {} cart",user.getEmail());
        } else {
            log.info("The quantity of good {} was reduced by one and now {}",cart.getProduct().getTitle(),user.getEmail());
            cart.setAmount(cart.getAmount() - 1);
            userCarts.set(index, cart);
        }
        user.setCarts(userCarts);
        userService.update(user);
        req.getRequestDispatcher(JspHelper.getPath("cart")).forward(req, resp);

    }
}
