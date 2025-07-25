package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.dto.response.AppointmentsAdminResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class AppointmentController {

    private final IAppointmentService appointmentService;

    @RequestMapping("/my-appointment")
    public String appointment() {
        return "user/appointment";
    }
}
