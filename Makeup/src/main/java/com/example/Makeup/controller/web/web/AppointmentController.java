package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.model.AppointmentDTO;
import com.example.Makeup.dto.model.OrderDTO;
import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.dto.response.UserAppointmentResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class AppointmentController {

    private final IAppointmentService appointmentService;

    @RequestMapping("/myAppointment")
    public String appointment(Model model, @ModelAttribute("user") UserDTO userDTO) {

        if (userDTO != null) {
            UUID userId = userDTO.getId();
            ApiResponse<List<UserAppointmentResponse>> appointment = appointmentService.getAppointmentByUserId(userId);
            model.addAttribute("myAppointment", appointment.getResult());
        }
        return "user/appointment";
    }
}
