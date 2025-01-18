package com.example.webapp.servlet.guest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Locale;

@WebServlet("/changeLanguage")
@Slf4j
public class ChangeLanguageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lang = request.getParameter("lang");
        if (lang != null) {
            Locale locale = Locale.forLanguageTag(lang);
            request.getSession().setAttribute("locale", locale);
        }
        String referer = request.getHeader("Referer");
        response.sendRedirect(referer != null ? referer : request.getContextPath());
    }

}