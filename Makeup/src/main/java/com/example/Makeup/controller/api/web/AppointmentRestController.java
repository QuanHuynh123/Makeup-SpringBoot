package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.AppointmentDTO;
import com.example.Makeup.dto.model.WeekAppointmentsDTO;
import com.example.Makeup.dto.request.AppointmentRequestDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentRestController {
    private final AppointmentService appointmentService;

    /**
     * Get 1 appointment by Month
     */
    @GetMapping("/by-month")
    public ApiResponse<List<WeekAppointmentsDTO>> getAppointmentsByMonth(
            @RequestParam int month,
            @RequestParam int year,
            @RequestParam(required = false) UUID staffID) {
        return appointmentService.getAppointmentsByMonth(month, year, staffID);
    }

    /**
     * Get all appointment by Month
     */
    @GetMapping
    public ApiResponse<List<AppointmentDTO>> getAllAppointments() {
        return appointmentService.getAllAppointmentsDetail();
    }

    /**
     * Get 1 appointment by ID
     */
    @GetMapping("/{id}")
    public ApiResponse<AppointmentDTO> getAppointmentById(@PathVariable("id") UUID appointmentId) {
        return appointmentService.getAppointmentById(appointmentId);
    }

    /**
     * Get 1 detail appointment by Id
     */
    @GetMapping("/appointmentDetail/{id}")
    public ApiResponse<AppointmentDTO> getAppointmentDetailById(@PathVariable UUID id) {
        return appointmentService.getAppointmentDetailById(id);

    }


    /**
     * Update 1 appointment :
     *  - staff
     *  - time
     *  - service
     */
    @PutMapping("/{id}")
    public ApiResponse<AppointmentDTO> updateAppointment(
            @PathVariable UUID appointmentId,
            @RequestBody AppointmentDTO appointmentDTO) {
            return  appointmentService.updateAppointment(appointmentId, appointmentDTO);
        }


    /**
     * Delete 1 appointment by Id
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteAppointment(@PathVariable UUID appointmentId) {
        return appointmentService.deleteAppointment(appointmentId);
    }

    /**
     * Create 1 appointment
     */
    @PostMapping("/create")
    public ApiResponse<AppointmentDTO> createAppointment(@RequestBody AppointmentRequestDTO appointment) {
        return  appointmentService.createAppointment(appointment);
    }

    /**
     * Get all booked appointment times of a staff member on a specific date.
     */
    @GetMapping("/by-date")
    public ApiResponse<List<AppointmentDTO>> getAppointmentsByDate(
            @RequestParam UUID staffId,
            @RequestParam LocalDate makeupDate) {

        return appointmentService.getAppointmentsByDateAndStaff(staffId, makeupDate);
    }

}