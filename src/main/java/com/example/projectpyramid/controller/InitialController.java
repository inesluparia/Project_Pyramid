package com.example.projectpyramid.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@ControllerAdvice
public class InitialController {


    /**
     * Very simple first controller, which is the user's starting point.
     * It has an @ControllerAdvice annotation as it also contains an
     * @ExceptionHandler method that must be used across all @Controller classes.
     */
    @GetMapping("/")
    public String renderIndex() {
        return "index";
    }

    @ExceptionHandler(Exception.class)
    public String anotherError(Model model, Exception exception) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "error.html";
    }

}
