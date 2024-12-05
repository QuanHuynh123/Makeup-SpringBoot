package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class HomeController {

    @RequestMapping("/home")
    public String home(ModelMap model , HttpSession session ){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println("Session User: " + userDTO); // Kiểm tra giá trị của userDTO
        if (userDTO != null) {
            model.addAttribute("user", userDTO);
        }
        return "user/index";
    }

    @RequestMapping("/")
    public String redirectToHome() {
        return "redirect:/home"; // Chuyển hướng đến /home
    }
}
