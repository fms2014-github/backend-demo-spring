package com.portfolio.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/basic")
public class BasicLoginController {

    @GetMapping("/home")
    public String home() {
        return "jsp/basic-home";
    }
}
