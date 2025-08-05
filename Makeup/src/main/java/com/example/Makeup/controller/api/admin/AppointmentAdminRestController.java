package com.example.Makeup.controller.api.admin;

import com.example.Makeup.dto.model.AppointmentDTO;
import com.example.Makeup.dto.request.UpdateAppointmentRequest;
import com.example.Makeup.dto.response.WeekAppointmentsDTO;
import com.example.Makeup.dto.request.AppointmentRequest;
import com.example.Makeup.dto.response.AppointmentsAdminResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/admin/appointments")
public class AppointmentAdminRestController {
    private final IAppointmentService appointmentService;

    @GetMapping
    public ApiResponse<List<AppointmentsAdminResponse>> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public ApiResponse<AppointmentDTO> getAppointment(@PathVariable("id") UUID appointmentId) {
        return appointmentService.getAppointmentById(appointmentId);
    }

    @GetMapping("/by-month")
    public ApiResponse<List<WeekAppointmentsDTO>> getApepointmentsByMonth(
            @RequestParam int month,
            @RequestParam int year,
            @RequestParam(required = false ) UUID staffID) {
        return appointmentService.getAppointmentsByMonth(month, year, staffID);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteAppointment(@PathVariable UUID id) {
        return appointmentService.deleteAppointment(id);
    }

    @GetMapping("/by-date")
    public ApiResponse<List<AppointmentDTO>> getAppointmentsByDate(
            @RequestParam UUID staffId,
            @RequestParam LocalDate makeupDate) {

        return appointmentService.getAppointmentsByDateAndStaff(staffId, makeupDate);
    }

    @PutMapping("/{id}")
    public ApiResponse<AppointmentDTO> updateAppointment(
            @PathVariable UUID id,
            @RequestBody UpdateAppointmentRequest appointmentDTO) {
        return appointmentService.updateAppointment(id, appointmentDTO);
    }


}
