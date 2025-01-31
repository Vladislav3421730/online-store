package com.example.webapp.controller;

import com.example.webapp.exception.EntityNotFoundException;
import com.example.webapp.exception.WrongIndexException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFoundException(EntityNotFoundException entityNotFoundException, HttpServletResponse response, Model model) {
        String message = entityNotFoundException.getMessage();
        model.addAttribute("message", message);
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return "error404";
    }

    @ExceptionHandler(WrongIndexException.class)
    public String handleWrongIndexException(WrongIndexException wrongIndexException, HttpServletResponse response, Model model) {
        String message = wrongIndexException.getMessage();
        model.addAttribute("message", message);
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return "error404";
    }



}
