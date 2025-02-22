package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.UserDTO;
import com.example.Makeup.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/user")
public class ProfileController {
    @GetMapping("/profile")
    public String profile(ModelMap modelMap){
        return "user/profile";
    }

}
