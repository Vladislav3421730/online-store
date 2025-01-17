package com.example.webapp.servlet.admin;

import com.example.webapp.dto.UserDto;
import com.example.webapp.model.User;
import com.example.webapp.service.UserService;
import com.example.webapp.service.impl.UserServiceImpl;
import com.example.webapp.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/admin/search")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class UserSearchServlet extends HttpServlet {

    UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String emailParam = req.getParameter("search");
        log.info("Trying to find user with email {}",emailParam);
        if(emailParam.isBlank()){
            log.info("email is empty");
            resp.sendRedirect(req.getContextPath() +"/admin/panel");
            return;
        }
        Optional<UserDto> user = userService.findByEmail(emailParam);
        if(user.isEmpty()){
            log.error("User with email {} was not found",emailParam);
            req.setAttribute("users", List.of());
        }
        else {
            req.setAttribute("users", List.of(user.get()));
        }
        req.setAttribute("search",emailParam);
        req.getRequestDispatcher(JspHelper.getPath("adminPanel")).forward(req,resp);
    }
}
