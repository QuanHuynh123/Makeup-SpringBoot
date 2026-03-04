package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.AppointmentDTO;
import com.example.Makeup.dto.model.FeedBackDTO;
import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.dto.request.AppointmentRequest;
import com.example.Makeup.dto.request.CreateFeedBackRequest;
import com.example.Makeup.dto.response.AppointmentsAdminResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IAppointmentService;
import com.example.Makeup.service.IFeedBackService;
import com.example.Makeup.service.common.RateLimitService;
import com.example.Makeup.utils.SecurityUserUtil;
import java.time.Duration;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Appointment API", description = "API for appointment operations")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentRestController {

  private final IAppointmentService appointmentService;
  private final RateLimitService rateLimitService;
  private final IFeedBackService feedBackService;

  @Operation(summary = "Get my appointments", description = "Retrieve a list of my appointments")
  @GetMapping("/my-appointments")
  public ApiResponse<List<AppointmentsAdminResponse>> getMyAppointments() {

    UserDTO userDTO = SecurityUserUtil.getCurrentUser();

    return ApiResponse.success("Get appointment by ID success",appointmentService.getAppointmentByUserId(userDTO.getId()));

  }

  @Operation(summary = "Create an appointment", description = "Create a new appointment")
  @PostMapping("/create")
  public ApiResponse<AppointmentDTO> createAppointment(
      @RequestBody AppointmentRequest appointment) {

    UserDTO userDTO = SecurityUserUtil.getCurrentUser();
    String key = "booking:" + userDTO.getId();

    if (rateLimitService.isRateLimited(key, 5, Duration.ofMinutes(1))) {
      return ApiResponse.error(
          429,
          "You have exceeded the maximum number of appointment bookings. Please try again later.");
    }

    return ApiResponse.success(
            "Create appointment success",appointmentService.createAppointment(appointment));
  }

  @Operation(summary = "Rate an appointment", description = "Create feedback for an appointment")
  @PostMapping("/feedback")
  public ApiResponse<FeedBackDTO> rateAppointment(
      @RequestBody CreateFeedBackRequest createFeedBackRequest) {
    return ApiResponse.success(
            "Create feedback success",feedBackService.createFeedBack(createFeedBackRequest));
  }
}
