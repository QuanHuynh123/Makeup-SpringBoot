package com.example.Makeup.service;

import com.example.Makeup.dto.model.AppointmentDTO;
import com.example.Makeup.dto.request.AppointmentRequest;
import com.example.Makeup.dto.request.UpdateAppointmentRequest;
import com.example.Makeup.dto.response.AppointmentsAdminResponse;
import com.example.Makeup.dto.response.WeekAppointmentsDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IAppointmentService {

  List<WeekAppointmentsDTO> getAppointmentsByMonth(int month, int year, UUID staffID);

  List<AppointmentsAdminResponse> getAllAppointments();

  AppointmentDTO getAppointmentById(UUID appointmentId);

  AppointmentDTO updateAppointment(
      UUID idAppointment, UpdateAppointmentRequest appointmentDTO);

  List<AppointmentsAdminResponse> getAppointmentByUserId(UUID userId);

  Boolean deleteAppointment(UUID appointmentId);

  AppointmentDTO createAppointment(AppointmentRequest newAppointment);

  List<AppointmentDTO> getAppointmentsByDateAndStaff(
      UUID staffId, LocalDate makeupDate);

  List<AppointmentDTO> rateAppointment(UUID appointmentId, UUID userId, int rating);
}
