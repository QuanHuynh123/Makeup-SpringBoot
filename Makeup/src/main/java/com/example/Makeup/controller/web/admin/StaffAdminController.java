package com.example.Makeup.controller.web.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/")
public class StaffAdminController {

    @GetMapping("staff")
    public String staffAdminPage() {
        return "admin/staff-admin";
    }
}
