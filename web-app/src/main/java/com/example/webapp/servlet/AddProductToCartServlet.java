package com.example.webapp.servlet;

import com.example.webapp.dto.ProductDto;
import com.example.webapp.dto.UserDto;
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

        ProductDto product = productService.findById(Validator
                .validateLong(req.getParameter("id")));
        if(product.getAmount()==0){
            log.info("The quantity of goods {} is zero",product.getTitle());
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        UserDto user = (UserDto) req.getSession().getAttribute("user");
        UserDto updatedUser = userService.addProductToCart(user,product);
        req.getSession().setAttribute("user",updatedUser);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
