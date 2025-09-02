package com.javarush.jira.login.internal.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/view/login")
    public String login() {
        return "login";
    }
}