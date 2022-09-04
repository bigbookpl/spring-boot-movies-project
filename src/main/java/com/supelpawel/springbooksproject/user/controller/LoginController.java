package com.supelpawel.springbooksproject.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    String login() {
        return "auth/login";
    }

    @GetMapping("/403")
    String showAccessDeniedPage() {
        return "auth/403";
    }
}
