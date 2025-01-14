package com.example.webapp.filter;

import com.example.webapp.dto.UserDto;
import com.example.webapp.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*"})
public class AdminFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        UserDto user = (UserDto) req.getSession().getAttribute("user");
        if(user==null){
            res.sendRedirect(req.getContextPath()+"/login");
            return;
        }
       if(!user.isAdmin()) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to access this resource.");
            return;
        }
        chain.doFilter(req,res);

    }
}
