package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.WeekAppointmentsDTO;
import com.example.Makeup.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentRestController {

    @Autowired
    private AppointmentService appointmentService;

    // API để lấy lịch hẹn theo tháng
    @GetMapping("/by-month")
    public List<WeekAppointmentsDTO> getAppointmentsByMonth(
            @RequestParam int month,
            @RequestParam int year) {
        return appointmentService.getAppointmentsByMonth(month, year);
    }
}
