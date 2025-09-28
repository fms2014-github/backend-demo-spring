package com.portfolio.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginFormController {

    @GetMapping("/login-page")
    public String loginPage() {
        return "thymeleaf/login-page";
    }

}
