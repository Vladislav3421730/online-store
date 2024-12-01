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

@WebServlet("/user/cart/delete")
@Slf4j
public class DeleteCartServlet extends HttpServlet {

    private final UserService userService= UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        List<Cart> cartAfterRemoving = user.getCarts();
        cartAfterRemoving.remove(Integer.parseInt(req.getParameter("index")));
        user.setCarts(cartAfterRemoving);
        log.info("The product has been removed from the user's {} cart",user.getEmail());
        req.getSession().setAttribute("user",userService.update(user));
        req.getRequestDispatcher(JspHelper.getPath("cart")).forward(req,resp);
    }
}
