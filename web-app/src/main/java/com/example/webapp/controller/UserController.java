package com.example.webapp.controller;

import com.example.webapp.dto.UserDto;
import com.example.webapp.service.AdminService;
import com.example.webapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    @GetMapping("/panel")
    public String getAdminPage(Model model) {
        List<UserDto> users = userService.findAll();
        model.addAttribute("users", users);
        return "adminPanel";
    }

    @PostMapping("/bun/{id}")
    public String bunUser(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);
        adminService.bun(userDto);
        return "redirect:/admin/panel";
    }

    @PostMapping("/role/manager/{id}")
    public String madeUserManager(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);
        adminService.madeManager(userDto);
        return "redirect:/admin/panel";
    }

    @GetMapping("/search")
    public String searchUser(@RequestParam("search") String emailParam, Model model) {
        log.info("Trying to find user with email {}", emailParam);
        if (emailParam.isBlank()) {
            log.info("Email is empty");
            return "redirect:/admin/panel";
        }
        Optional<UserDto> user = userService.findByEmail(emailParam);
        if (user.isEmpty()) {
            log.error("User with email {} was not found", emailParam);
            model.addAttribute("users", Collections.emptyList());
        } else {
            model.addAttribute("users", List.of(user.get()));
        }
        model.addAttribute("search", emailParam);
        return "adminPanel";
    }

    @GetMapping("/{id}/orders")
    public String findUserOrdersByUserId(@PathVariable Long id ,Model model) {
        List<UserDto> users = userService.findAll();
        model.addAttribute("users", users);
        return "adminPanel";
    }
}
