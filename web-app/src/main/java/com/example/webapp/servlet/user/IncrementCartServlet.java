package com.example.webapp.servlet.user;

import com.example.webapp.dto.CartDto;
import com.example.webapp.dto.UserDto;
import com.example.webapp.service.CartService;
import com.example.webapp.service.UserService;
import com.example.webapp.service.impl.CartServiceImpl;
import com.example.webapp.service.impl.UserServiceImpl;
import com.example.webapp.utils.JspHelper;
import com.example.webapp.utils.Messages;
import com.example.webapp.utils.ResourceBundleUtils;
import com.example.webapp.utils.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet("/user/cart/increment")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class IncrementCartServlet extends HttpServlet {

    CartService cartService = CartServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDto user = (UserDto) req.getSession().getAttribute("user");
        int index = Validator.validateInt(req.getParameter("index"));
        List<CartDto> userCarts = user.getCarts();

        ResourceBundle messages = ResourceBundleUtils.get(req);

        if (!cartService.incrementAmountOfCartInBasket(userCarts, index)) {
            BigDecimal totalPrice = BigDecimal.valueOf(Validator.validateDouble(req.getParameter("totalCoast")));
            req.setAttribute("totalCoast", totalPrice);
            req.setAttribute("error", messages.getString(Messages.ERROR_MESSAGE_INCREMENT));
            req.getRequestDispatcher(JspHelper.getPath("cart")).forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/user/cart");
    }
}
