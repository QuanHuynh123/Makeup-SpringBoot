package com.example.Makeup.controller.web.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String login(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            System.out.println("anonymous");
        } else {
            System.out.println("not anonymous");
        }
        return "/login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }
    
}
