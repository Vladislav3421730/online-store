package com.example.webapp.servlet;

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

@WebServlet("/registration")
@Slf4j
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        req.setAttribute("username", username);
        req.setAttribute("email", email);
        req.setAttribute("password", password);

        if (userService.findByEmail(email).isPresent()) {
            log.error("user with email {} already exists",email);
            req.setAttribute("error", "Пользователь с таким email уже существует, выберите другой");
            req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
            return;
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();

        userService.save(user);
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}