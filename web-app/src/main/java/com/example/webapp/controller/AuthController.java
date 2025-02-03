package com.example.webapp.controller;

import com.example.webapp.dto.RegisterUserDto;
import com.example.webapp.service.UserService;
import com.example.webapp.utils.Messages;
import com.example.webapp.utils.ValidatorUtils;
import jakarta.validation.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthController {

    UserService userService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String userRegistration(@ModelAttribute("registerUserDto") RegisterUserDto registerUserDto,
                                   Model model) {
        model.addAttribute("registerUserDto", registerUserDto);
        Set<ConstraintViolation<RegisterUserDto>> violations = ValidatorUtils.getValidator().validate(registerUserDto);
        if (!violations.isEmpty()) {
            List<String> errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .toList();
            model.addAttribute("errors", errorMessages);
            return "registration";
        }

        if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword())) {
            model.addAttribute("error", Messages.ERROR_PASSWORD_MISS);
            return "registration";
        }
        if (userService.findByEmail(registerUserDto.getEmail()).isPresent()) {
            model.addAttribute("error", Messages.EMAIL_ALREADY_IN_USE);
            return "registration";
        }
        if (userService.findByPhoneNumber(registerUserDto.getPhoneNumber()).isPresent()) {
            model.addAttribute("error", Messages.PHONE_ALREADY_IN_USE);
            return "registration";
        }
        userService.save(registerUserDto);

        return "redirect:/login";
    }

    @GetMapping("/denied")
    public String getDeniedPage() {
        return "error403";
    }


}
