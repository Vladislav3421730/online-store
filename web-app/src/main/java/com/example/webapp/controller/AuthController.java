package com.example.webapp.controller;

import com.example.webapp.dto.RegisterUserDto;
import com.example.webapp.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
    public String getRegistrationPage(Model model) {
        model.addAttribute("registerUserDto", RegisterUserDto.builder().build());
        return "registration";
    }

    @PostMapping("/registration")
    public String userRegistration(@Valid @ModelAttribute("registerUserDto") RegisterUserDto registerUserDto,
                                   BindingResult bindingResult,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            model.addAttribute("errors", errorMessages);
            return "registration";
        }

        if (userService.findByEmail(registerUserDto.getEmail()).isPresent()) {
            model.addAttribute("errors", List.of("Email is already in use."));
            return "registration";
        }
        if (userService.findByPhoneNumber(registerUserDto.getPhoneNumber()).isPresent()) {
            model.addAttribute("errors", List.of("Phone number is already in use."));
            return "registration";
        }
        userService.save(registerUserDto);

        return "redirect:/login?success=true";
    }


}
