package com.example.Makeup.service;

import com.example.Makeup.dto.model.StaffDTO;
import com.example.Makeup.dto.request.CreateStaffRequest;
import com.example.Makeup.dto.response.StaffAccountResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import java.util.List;
import java.util.UUID;

public interface IStaffService {

  List<StaffDTO> getAllStaff();

  StaffAccountResponse getStaffById(UUID staffId);

  StaffDTO addStaff(CreateStaffRequest newStaff);

  String deleteStaffIfNoAppointments(UUID staffId);

  StaffDTO getStaffDetailById(UUID staffId);

  StaffDTO updateStaff(StaffDTO staffDTO, UUID staffId);
}
