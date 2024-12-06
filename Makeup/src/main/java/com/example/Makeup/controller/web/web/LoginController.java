package com.example.Makeup.controller.web.web;

import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/login")
    public String login(){
        return "/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.setAttribute("user",null);
        session.removeAttribute("user");
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }
    
}
