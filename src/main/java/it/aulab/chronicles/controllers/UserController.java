package it.aulab.chronicles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import it.aulab.chronicles.services.UserService;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "home";
    }    
}
