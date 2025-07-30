package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.dto.response.AppointmentsAdminResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IAppointmentService;
import com.example.Makeup.utils.SecurityUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentRestController {

    private final IAppointmentService appointmentService;

    @GetMapping("/my-appointments")
    public ApiResponse<List<AppointmentsAdminResponse>> getMyAppointments( ) {

        UserDTO userDTO = SecurityUserUtil.getCurrentUser();

        return appointmentService.getAppointmentByUserId(userDTO.getId());
    }
}