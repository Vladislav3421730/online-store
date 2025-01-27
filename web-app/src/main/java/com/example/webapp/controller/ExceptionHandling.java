package com.example.webapp.controller;

import com.example.webapp.exception.EntityNotFoundException;
import com.example.webapp.exception.WrongIndexException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFoundException(EntityNotFoundException entityNotFoundException, Model model) {
        String message = entityNotFoundException.getMessage();
        model.addAttribute("message", message);
        return "error404";
    }

    @ExceptionHandler(WrongIndexException.class)
    public String handleWrongIndexException(WrongIndexException wrongIndexException, Model model) {
        String message = wrongIndexException.getMessage();
        model.addAttribute("message", message);
        return "error404";
    }


}
