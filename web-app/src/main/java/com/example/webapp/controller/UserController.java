package com.example.webapp.controller;

import com.example.webapp.model.User;
import com.example.webapp.wrappers.UserDetailsWrapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class UserController {


    @GetMapping("/account")
    public String getAccountPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetailsWrapper userDetailsWrapper = (UserDetailsWrapper) authentication.getPrincipal();
            model.addAttribute("user", userDetailsWrapper.user());
        }
        return "account";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetailsWrapper userDetailsWrapper = (UserDetailsWrapper) authentication.getPrincipal();
            model.addAttribute("user", userDetailsWrapper.user());
        }
        return "cart";
    }



}
