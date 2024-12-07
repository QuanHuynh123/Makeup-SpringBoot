package com.example.Makeup.controller.web.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        // Xóa tất cả session attributes
        session.invalidate();

        // Xóa Model attributes nếu có (tuỳ thuộc vào ứng dụng của bạn)
        model.asMap().clear(); // Xóa tất cả model attributes

        return "redirect:/login"; // Chuyển hướng đến trang đăng nhập
    }


    @GetMapping("/register")
    public String register(){
        return "register";
    }
    
}
