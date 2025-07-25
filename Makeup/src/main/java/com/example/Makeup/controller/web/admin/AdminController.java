package com.example.Makeup.controller.web.admin;
import com.example.Makeup.dto.model.AppointmentDTO;
import com.example.Makeup.dto.model.StaffDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.repository.AppointmentRepository;
import com.example.Makeup.repository.OrderRepository;
import com.example.Makeup.service.IAppointmentService;
import com.example.Makeup.service.IStaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/")
public class AdminController {

    private final IStaffService staffService;

    @GetMapping("home")
    public String adminPage() {
        return "admin/admin";
    }

    @GetMapping("staff")
    public String staffAdminPage(Model model) {
        ApiResponse<List<StaffDTO>> staffList = staffService.getAllStaffDetail();
        model.addAttribute("staffList", staffList);
        return "admin/staff-all";
    }
}