package com.example.webapp.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LocaleController {

    @RequestMapping("/changeLanguage")
    public String setLanguage(
            @RequestParam("lang") String lang,
            @RequestHeader(value = "Referer", defaultValue = "/products") String referer,
            HttpSession session) {
        session.setAttribute("locale", lang);
        return "redirect:" + referer;
    }
}