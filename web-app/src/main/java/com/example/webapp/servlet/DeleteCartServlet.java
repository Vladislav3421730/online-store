package com.example.webapp.servlet;

import com.example.webapp.model.Cart;
import com.example.webapp.model.User;
import com.example.webapp.service.UserService;
import com.example.webapp.service.impl.UserServiceImpl;
import com.example.webapp.utils.JspHelper;
import com.example.webapp.validator.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/cart/delete")
@Slf4j
public class DeleteCartServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        int index = Validator.validateInt(req.getParameter("index"));
        List<Cart> cartAfterRemoving = user.getCarts();
        if (index < 0 || index >= cartAfterRemoving.size()) {
            log.error("Index out of bounds {}",index);
            throw new RuntimeException("Index out of bounds: " + index);
        }
        cartAfterRemoving.remove(index);
        user.setCarts(cartAfterRemoving);
        log.info("The product has been removed from the user's {} cart", user.getEmail());
        req.getSession().setAttribute("user", userService.update(user));
        resp.sendRedirect(req.getContextPath()+"/user/cart");
    }

}
