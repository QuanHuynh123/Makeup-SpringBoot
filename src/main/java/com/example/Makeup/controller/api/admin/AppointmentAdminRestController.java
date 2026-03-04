package com.example.Makeup.controller.api.admin;

import com.example.Makeup.dto.model.AppointmentDTO;
import com.example.Makeup.dto.request.UpdateAppointmentRequest;
import com.example.Makeup.dto.response.AppointmentsAdminResponse;
import com.example.Makeup.dto.response.WeekAppointmentsDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IAppointmentService;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Appointment Admin API", description = "API for admin appointment operations")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/admin/appointments")
public class AppointmentAdminRestController {
  private final IAppointmentService appointmentService;

  @Operation(summary = "Get all appointments", description = "Retrieve a list of all appointments")
  @GetMapping
  public ApiResponse<List<AppointmentsAdminResponse>> getAllAppointments() {
    return ApiResponse.success("Get all appointments success",appointmentService.getAllAppointments());
  }

  @Operation(summary = "Get appointment by ID", description = "Retrieve appointment details by appointment ID")
  @GetMapping("/{id}")
  public ApiResponse<AppointmentDTO> getAppointment(@PathVariable("id") UUID appointmentId) {
    return ApiResponse.success("Get appointment by ID success",appointmentService.getAppointmentById(appointmentId));
  }

  @Operation(summary = "Get appointments by month", description = "Retrieve appointments for a specific month and year, optionally filtered by staff ID")
  @GetMapping("/by-month")
  public ApiResponse<List<WeekAppointmentsDTO>> getApepointmentsByMonth(
      @RequestParam int month,
      @RequestParam int year,
      @RequestParam(required = false) UUID staffID) {
    return ApiResponse.success(
            "Get appointments by month success",appointmentService.getAppointmentsByMonth(month, year, staffID));
  }

  @Operation(summary = "Delete appointment", description = "Delete an appointment by appointment ID")
  @DeleteMapping("/{id}")
  public ApiResponse<Boolean> deleteAppointment(@PathVariable UUID id) {
    return ApiResponse.success("Delete appointment success",appointmentService.deleteAppointment(id));
  }

  @Operation(summary = "Get appointments by date", description = "Retrieve appointments for a specific date and staff ID")
  @GetMapping("/by-date")
  public ApiResponse<List<AppointmentDTO>> getAppointmentsByDate(
      @RequestParam UUID staffId, @RequestParam LocalDate makeupDate) {

    return ApiResponse.success("Get appointments success",appointmentService.getAppointmentsByDateAndStaff(staffId, makeupDate));
  }

  @Operation(summary = "Update appointment", description = "Update an appointment by appointment ID")
  @PutMapping("/{id}")
  public ApiResponse<AppointmentDTO> updateAppointment(
      @PathVariable UUID id, @RequestBody UpdateAppointmentRequest appointmentDTO) {
    return ApiResponse.success("Update appointment success",appointmentService.updateAppointment(id, appointmentDTO));
  }
}
