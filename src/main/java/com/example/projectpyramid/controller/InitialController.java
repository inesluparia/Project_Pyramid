package com.example.projectpyramid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InitialController {

    @GetMapping("/")
    public String renderIndex() {
        return "index";
    }

    @ExceptionHandler(Exception.class)
    public String anotherError(Model model, Exception exception) {
        model.addAttribute("message", exception.getMessage() + "\n\n" + exception.getClass());
        return "errorpage.html";
    }

}
