package com.example.Makeup.controller.web.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

  @GetMapping("/profile")
  public String profile(ModelMap modelMap) {
    return "user/profile";
  }
}
