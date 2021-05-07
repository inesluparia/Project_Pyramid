package com.example.projectpyramid.controller;
import com.example.projectpyramid.data_access.UserMapper;
import com.example.projectpyramid.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    UserMapper userMapper = new UserMapper();

    @GetMapping("/")
    public String renderIndex(){
        return "index.html";
    }


    @GetMapping("/logintest")
    @ResponseBody
    public String login() throws Exception {
        User user = userMapper.login("Andersand","1234");
        return user.getUserName();
    }
}
