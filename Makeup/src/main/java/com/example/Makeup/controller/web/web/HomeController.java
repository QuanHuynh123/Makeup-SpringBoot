package com.example.Makeup.controller.web.web;
import com.example.Makeup.dto.UserDTO;
import com.example.Makeup.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {

    @RequestMapping("/home")
    public String home(ModelMap model , HttpSession session) throws InterruptedException {
        return "user/index";
    }

    @RequestMapping("/")
    public String redirectToHome() {
        return "redirect:/home"; // Chuyển hướng đến /home
    }
}
