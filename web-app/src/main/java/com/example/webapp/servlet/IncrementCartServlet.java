package com.example.webapp.servlet;

import com.example.webapp.dto.CartDto;
import com.example.webapp.dto.UserDto;
import com.example.webapp.service.CartService;
import com.example.webapp.service.UserService;
import com.example.webapp.service.impl.CartServiceImpl;
import com.example.webapp.service.impl.UserServiceImpl;
import com.example.webapp.utils.JspHelper;
import com.example.webapp.utils.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/user/cart/increment")
@Slf4j
public class IncrementCartServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();
    private final CartService cartService = CartServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDto user = (UserDto) req.getSession().getAttribute("user");
        int index = Validator.validateInt(req.getParameter("index"));
        List<CartDto> userCarts = user.getCarts();
        if (!cartService.incrementAmountOfCartInBasket(userCarts, index)) {
            BigDecimal totalPrice = BigDecimal.valueOf(Validator.validateDouble(req.getParameter("totalCoast")));
            req.setAttribute("totalCoast", totalPrice);
            req.setAttribute("error", "Ошибка, вы не можете указать количество товара большее, чем есть на складе");
            req.getRequestDispatcher(JspHelper.getPath("cart")).forward(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/user/cart");
    }
}
