package com.example.Makeup.service;

import com.example.Makeup.dto.model.AppointmentDTO;
import com.example.Makeup.dto.model.WeekAppointmentsDTO;
import com.example.Makeup.dto.request.AppointmentRequestDTO;
import com.example.Makeup.dto.response.UserAppointmentResponse;
import com.example.Makeup.dto.response.common.ApiResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IAppointmentService {

    ApiResponse<List<WeekAppointmentsDTO>> getAppointmentsByMonth(int month, int year, UUID staffID);
    ApiResponse<List<AppointmentDTO>> getAllAppointments();
    ApiResponse<AppointmentDTO> getAppointmentById(UUID appointmentId);
    ApiResponse<AppointmentDTO> updateAppointment(UUID idAppointment, AppointmentDTO appointmentDTO);
    ApiResponse<List<UserAppointmentResponse>> getAppointmentByUserId(UUID userId);
    ApiResponse<Boolean> deleteAppointment(UUID appointmentId);
    ApiResponse<AppointmentDTO> createAppointment(AppointmentRequestDTO newAppointment);
    ApiResponse<List<AppointmentDTO>> getAppointmentsByDateAndStaff(UUID staffId, LocalDate makeupDate);
}
