package com.example.webapp.servlet;

import com.example.webapp.model.Order;
import com.example.webapp.model.User;
import com.example.webapp.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Comparator;

@WebServlet("/user/account")
public class ViewAccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        user.getOrders().sort(Comparator.comparing(Order::getCreatedAt).reversed());
        req.getRequestDispatcher(JspHelper.getPath("account")).forward(req,resp);
    }
}
