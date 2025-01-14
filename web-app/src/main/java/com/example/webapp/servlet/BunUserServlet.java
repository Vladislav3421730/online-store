package com.example.webapp.servlet;

import com.example.webapp.dto.UserDto;
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

@WebServlet("/admin/bun")
@Slf4j
public class BunUserServlet extends HttpServlet {

    private final AdminService adminService = AdminServiceImpl.getInstance();
    private final UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto user = userService.findById(Validator
                .validateLong(req.getParameter("userId")));
        log.info("{} {}", user.isBun() ? "ban user" : "unban",user.getEmail());
        adminService.bun(user);
        resp.sendRedirect(req.getContextPath()+"/admin/panel");
    }
}
