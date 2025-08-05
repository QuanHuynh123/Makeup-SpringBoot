package com.example.Makeup.service;

import com.example.Makeup.dto.model.AppointmentDTO;
import com.example.Makeup.dto.request.UpdateAppointmentRequest;
import com.example.Makeup.dto.response.WeekAppointmentsDTO;
import com.example.Makeup.dto.request.AppointmentRequest;
import com.example.Makeup.dto.response.AppointmentsAdminResponse;
import com.example.Makeup.dto.response.common.ApiResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IAppointmentService {

    ApiResponse<List<WeekAppointmentsDTO>> getAppointmentsByMonth(int month, int year, UUID staffID);
    ApiResponse<List<AppointmentsAdminResponse>> getAllAppointments();
    ApiResponse<AppointmentDTO> getAppointmentById(UUID appointmentId);
    ApiResponse<AppointmentDTO> updateAppointment(UUID idAppointment, UpdateAppointmentRequest appointmentDTO);
    ApiResponse<List<AppointmentsAdminResponse>> getAppointmentByUserId(UUID userId);
    ApiResponse<Boolean> deleteAppointment(UUID appointmentId);
    ApiResponse<AppointmentDTO> createAppointment(AppointmentRequest newAppointment);
    ApiResponse<List<AppointmentDTO>> getAppointmentsByDateAndStaff(UUID staffId, LocalDate makeupDate);
}
