package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.ApiResponse;
import com.example.Makeup.dto.AppointmentDTO;
import com.example.Makeup.dto.AppointmentDetailDTO;
import com.example.Makeup.dto.WeekAppointmentsDTO;
import com.example.Makeup.entity.Appointment;
import com.example.Makeup.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentRestController {

    @Autowired
    private AppointmentService appointmentService;

    /**
     * Get 1 appointment by Month
     */
    @GetMapping("/by-month")
    public List<WeekAppointmentsDTO> getAppointmentsByMonth(
            @RequestParam int month,
            @RequestParam int year,
            @RequestParam(required = false) Integer staffID) {
        return appointmentService.getAppointmentsByMonth(month, year, staffID);
    }

    /**
     * Get all appointment by Month
     */
    @GetMapping
    public ResponseEntity<List<AppointmentDetailDTO>> getAllAppointments() {
        List<AppointmentDetailDTO> appointments = appointmentService.getAllAppointmentsDetail();
        return ResponseEntity.ok(appointments);
    }

    /**
     * Get 1 appointment by ID
     */
    @GetMapping("/{id}")
    public AppointmentDTO getAppointmentById(@PathVariable int id) {
        return appointmentService.getAppointmentById(id);
    }

    /**
     * Get 1 detail appointment by Id
     */
    @GetMapping("/appointmentDetail/{id}")
    public ResponseEntity<AppointmentDetailDTO> getAppointmentDetailById(@PathVariable int id) {
        AppointmentDetailDTO appointment = appointmentService.getAppointmentDetailById(id);
        if (appointment != null) {
            return ResponseEntity.ok(appointment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    /**
     * Update 1 appointment :
     *  - staff
     *  - time
     *  - service
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentDTO>> updateAppointment(
            @PathVariable int id,
            @RequestBody AppointmentDTO appointmentDTO) {
        try {
            ApiResponse<AppointmentDTO> response = appointmentService.updateAppointment(id, appointmentDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Trả về thông báo lỗi
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<AppointmentDTO>builder()
                            .code(400)
                            .message("Có lỗi xảy ra: " + e.getMessage())
                            .build());
        }
    }


    /**
     * Delete 1 appointment by Id
     */
    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable int id) {
        appointmentService.deleteAppointment(id);
    }

    /**
     * Create 1 appointment
     */
    @PostMapping("/create")
    public ResponseEntity<?> addAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        try {
            AppointmentDTO savedAppointment = appointmentService.addAppointment(appointmentDTO);
            return ResponseEntity.ok(savedAppointment);
        } catch (RuntimeException e) {
            // Trả về thông báo lỗi dưới dạng chuỗi
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Get all booked appointment times of a staff member on a specific date.
     */
    @GetMapping("/by-date")
    public ResponseEntity<?> getAppointmentsByDate(
            @RequestParam int staffId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate makeupDate) {

        Date sqlDate = Date.valueOf(makeupDate);
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDateAndStaff(staffId, sqlDate);

        if (appointments.isEmpty()) {
            return ResponseEntity.ok("No appointment");
        }

        return ResponseEntity.ok(appointments);
    }

}