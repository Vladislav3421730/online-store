package com.example.webapp.servlet;

import com.example.webapp.model.User;
import com.example.webapp.service.AdminService;
import com.example.webapp.service.UserService;
import com.example.webapp.service.impl.AdminServiceImpl;
import com.example.webapp.service.impl.UserServiceImpl;
import com.example.webapp.utils.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebServlet("/admin/role/manager")
@Slf4j
public class AdminMadeManagerServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();
    private final AdminService adminService = AdminServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = userService.findById(Validator
                .validateLong(req.getParameter("userId")));
        log.info("Attempting to change ROLE_MANAGER for user with ID: {}", user.getId());
        adminService.madeManager(user);
        resp.sendRedirect(req.getContextPath()+"/admin/panel");
    }
}
