package com.example.webapp.servlet;

import com.example.webapp.model.Cart;
import com.example.webapp.model.Product;
import com.example.webapp.model.User;
import com.example.webapp.service.ProductService;
import com.example.webapp.service.UserService;
import com.example.webapp.service.impl.ProductServiceImpl;
import com.example.webapp.service.impl.UserServiceImpl;
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

        String idParam = req.getParameter("id");
        Product product;
        try {
            long productId = Long.parseLong(idParam);
            product = productService.findById(productId);
        } catch (NumberFormatException e) {
            log.error("Failed to parse in Long product id {}",idParam);
            throw new RuntimeException("Invalid product ID: " + idParam, e);
        }
        User user = (User) req.getSession().getAttribute("user");

        boolean isInCartList = user.getCarts().stream()
                .filter(cart -> cart.getProduct().getId().equals(product.getId()))
                .peek(cart -> cart.setAmount(cart.getAmount() + 1))
                .findFirst()
                .isPresent();

        if (!isInCartList) {
            log.info("a new item {} has been added to the user's {} cart",product.getTitle(),user.getEmail());
            Cart cart = new Cart(product);
            user.addCartToList(cart);
        }
        req.getSession().setAttribute("user", userService.update(user));
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
