package com.example.Makeup.controller.web.admin;

import com.example.Makeup.service.IStaffService;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/")
public class AdminController {

  private final IStaffService staffService;

  @GetMapping("home")
  public String adminPage() {
    return "admin/admin";
  }
}
