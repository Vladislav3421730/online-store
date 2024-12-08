package com.example.webapp.servlet;

import com.example.webapp.model.Product;
import com.example.webapp.model.User;
import com.example.webapp.service.ProductService;
import com.example.webapp.service.UserService;
import com.example.webapp.service.impl.ProductServiceImpl;
import com.example.webapp.service.impl.UserServiceImpl;
import com.example.webapp.utils.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebServlet("/user/cart/add")
@Slf4j
public class AddProductToCartServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();
    private final ProductService productService = ProductServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Product product=productService.findById(Validator
                .validateLong(req.getParameter("id")));
        if(product.getAmount()==0){
            log.info("The quantity of goods {} is zero",product.getTitle());
            req.setAttribute("error","Попробуйте в другой раз, когда товар будет на складе");
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        User user = (User) req.getSession().getAttribute("user");
        req.getSession().setAttribute("user", userService.addProductToCart(user,product));
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
