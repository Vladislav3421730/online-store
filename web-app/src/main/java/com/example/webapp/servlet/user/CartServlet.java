package com.example.webapp.servlet.user;

import com.example.webapp.dto.AddressDto;
import com.example.webapp.dto.OrderDto;
import com.example.webapp.dto.UserDto;
import com.example.webapp.service.UserService;
import com.example.webapp.service.impl.UserServiceImpl;
import com.example.webapp.utils.*;
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
import java.util.ResourceBundle;

@WebServlet("/user/cart")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class CartServlet extends HttpServlet {

    UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDto user = (UserDto) req.getSession().getAttribute("user");
        if (!user.getCarts().isEmpty()) {
            BigDecimal totalCoast = user.getCarts().stream()
                    .map(x -> x.getProduct().getCoast().multiply(BigDecimal.valueOf(x.getAmount())))
                    .reduce(BigDecimal::add).get();
            log.info("The sum of the prices of all items in the basket {}", totalCoast);
            req.setAttribute("totalCoast", totalCoast);
        }
        AddressFormationAssistant.updateAddresses(req);
        req.getRequestDispatcher(JspHelper.getPath("cart")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto user = (UserDto) req.getSession().getAttribute("user");
        BigDecimal totalPrice = BigDecimal.valueOf(Validator.validateDouble(req.getParameter("totalCoast")));

        ResourceBundle messages = ResourceBundleUtils.get(req);

        if (!OrderPayingValidator.validateOrderCoast(totalPrice)) {
            req.setAttribute("error", messages.getString(Messages.FAILED_PAYMENT_MESSAGE));
            req.setAttribute("totalCoast", totalPrice);
            req.getRequestDispatcher(JspHelper.getPath("cart")).forward(req, resp);
            return;
        }

        AddressDto address = AddressFormationAssistant.formAddress(req);
        OrderDto order = new OrderDto(totalPrice);
        order.setAddress(address);

        UserDto updatedUser = userService.makeOrder(user, order);
        req.getSession().setAttribute("user", updatedUser);
        req.setAttribute("success", messages.getString(Messages.SUCCESS_MESSAGE));
        req.getRequestDispatcher(JspHelper.getPath("cart")).forward(req, resp);
    }


}
