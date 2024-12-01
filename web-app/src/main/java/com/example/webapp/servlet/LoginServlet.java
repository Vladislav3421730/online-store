package com.example.webapp.servlet;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.webapp.model.User;
import com.example.webapp.service.UserService;
import com.example.webapp.service.impl.UserServiceImpl;
import com.example.webapp.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("login")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        req.setAttribute("email", email);

        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty() || !BCrypt.verifyer().verify(password.toCharArray(), user.get().getPassword()).verified) {
            req.setAttribute("error","Неверный email или пароль");
            req.getRequestDispatcher(JspHelper.getPath("login")).forward(req, resp);
            return;
        }
        if(user.get().isBun()){
            req.setAttribute("error","Вы были забанены");
            req.getRequestDispatcher(JspHelper.getPath("login")).forward(req, resp);
            return;
        }
        req.getSession().setAttribute("user",user.get());
        resp.sendRedirect(req.getContextPath()+ "/");

    }
}
