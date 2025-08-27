package com.example.Makeup.service;

import com.example.Makeup.dto.model.StaffDTO;
import com.example.Makeup.dto.request.CreateStaffRequest;
import com.example.Makeup.dto.response.StaffAccountResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import java.util.List;
import java.util.UUID;

public interface IStaffService {

  ApiResponse<List<StaffDTO>> getAllStaff();

  ApiResponse<StaffAccountResponse> getStaffById(UUID staffId);

  ApiResponse<StaffDTO> addStaff(CreateStaffRequest newStaff);

  ApiResponse<String> deleteStaffIfNoAppointments(UUID staffId);

  ApiResponse<StaffDTO> getStaffDetailById(UUID staffId);

  ApiResponse<StaffDTO> updateStaff(StaffDTO staffDTO, UUID staffId);
}
