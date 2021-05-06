package com.example.projectpyramid.PyramidControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPyramidController {

    @GetMapping("/")
    public String renderMain(){

        System.out.println("Im here");
        return "main.html";

    }

}
