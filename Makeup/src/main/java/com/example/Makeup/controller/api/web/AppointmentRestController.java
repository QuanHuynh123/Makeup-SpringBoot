package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.AppointmentDTO;
import com.example.Makeup.dto.WeekAppointmentsDTO;
import com.example.Makeup.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public List<AppointmentDTO> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    // Lấy cuộc hẹn theo ID
    @GetMapping("/{id}")
    public AppointmentDTO getAppointmentById(@PathVariable int id) {
        return appointmentService.getAppointmentById(id);
    }

    // Thêm mới một cuộc hẹn
    @PostMapping
    public AppointmentDTO createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        return appointmentService.createAppointment(appointmentDTO);
    }

    // Cập nhật một cuộc hẹn
    @PutMapping("/{id}")
    public AppointmentDTO updateAppointment(@PathVariable int id, @RequestBody AppointmentDTO appointmentDTO) {
        return appointmentService.updateAppointment(id, appointmentDTO);
    }

    // Xóa một cuộc hẹn
    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable int id) {
        appointmentService.deleteAppointment(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> addAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        try {
            AppointmentDTO savedAppointment = appointmentService.addAppointment(appointmentDTO);
            return ResponseEntity.ok(savedAppointment);
        } catch (RuntimeException e) {
            // Trả về thông báo lỗi dưới dạng chuỗi
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
