package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.ApiResponse;
import com.example.Makeup.dto.AppointmentDTO;
import com.example.Makeup.dto.AppointmentDetailDTO;
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
            @RequestParam int year,
            @RequestParam(required = false) Integer staffID) {
        return appointmentService.getAppointmentsByMonth(month, year, staffID);
    }

//    @GetMapping
//    public List<AppointmentDTO> getAllAppointments() {
//        return appointmentService.getAllAppointments();
//    }

    @GetMapping
    public ResponseEntity<List<AppointmentDetailDTO>> getAllAppointments() {
        List<AppointmentDetailDTO> appointments = appointmentService.getAllAppointmentsDetail();
        return ResponseEntity.ok(appointments);
    }

    // Lấy cuộc hẹn theo ID
    @GetMapping("/{id}")
    public AppointmentDTO getAppointmentById(@PathVariable int id) {
        return appointmentService.getAppointmentById(id);
    }

    @GetMapping("/appointmentDetail/{id}")
    public ResponseEntity<AppointmentDetailDTO> getAppointmentDetailById(@PathVariable int id) {
        AppointmentDetailDTO appointment = appointmentService.getAppointmentDetailById(id);
        if (appointment != null) {
            return ResponseEntity.ok(appointment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    // Thêm mới một cuộc hẹn
    @PostMapping
    public AppointmentDTO createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        return appointmentService.createAppointment(appointmentDTO);
    }

    // Cập nhật một cuộc hẹn
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentDTO>> updateAppointment(
            @PathVariable int id,
            @RequestBody AppointmentDTO appointmentDTO) {
        try {
            // Gọi hàm update từ Service và trả về phản hồi thành công
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}