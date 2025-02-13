package com.example.webapp.servlet.authorization;

import com.example.webapp.dto.RegisterUserDto;
import com.example.webapp.model.User;
import com.example.webapp.service.UserService;
import com.example.webapp.service.impl.UserServiceImpl;
import com.example.webapp.utils.JspHelper;
import com.example.webapp.utils.Messages;
import com.example.webapp.utils.ResourceBundleUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet("/registration")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class RegistrationServlet extends HttpServlet {

    UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        String confirmPassword = req.getParameter("confirmPassword");

        req.setAttribute("username", username);
        req.setAttribute("email", email);
        req.setAttribute("password", password);
        req.setAttribute("phone", phone);

        ResourceBundle messages = ResourceBundleUtils.get(req);

        if (username.length() < 5 || email.isEmpty() || password.length() < 6) {
            log.error("the fields were not very well filled " +
                    "username: {} password {} email {}", username, password, email);
            req.setAttribute("error", messages.getString(Messages.INVALID_FIELDS));
            req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
            return;
        }
        if (userService.findByEmail(email).isPresent()) {
            log.error("user with email {} already exists", email);
            req.setAttribute("error", messages.getString(Messages.EMAIL_ALREADY_IN_USE));
            req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
            return;
        }
        if (userService.findByPhoneNumber(phone).isPresent()) {
            log.error("user with phone number {} already exists", phone);
            req.setAttribute("error", messages.getString(Messages.PHONE_ALREADY_IN_USE));
            req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
            return;
        }
        if (!password.equals(confirmPassword)) {
            log.error("password and confirmPassword are not the same {}, {}", password, confirmPassword);
            req.setAttribute("error", messages.getString(Messages.ERROR_PASSWORD_MISS));
            req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
            return;
        }

        RegisterUserDto registerUserDto = RegisterUserDto.builder()
                .username(username)
                .email(email)
                .password(password)
                .phoneNumber(phone)
                .build();

        userService.save(registerUserDto);
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
