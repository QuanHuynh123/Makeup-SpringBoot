package com.example.Makeup.controller.api.admin;

import com.example.Makeup.dto.model.StaffDTO;
import com.example.Makeup.dto.request.CreateStaffRequest;
import com.example.Makeup.dto.response.StaffAccountResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IStaffService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/staffs")
public class StaffAdminRestController {

  private final IStaffService staffService;

  @GetMapping()
  public ApiResponse<List<StaffDTO>> getAllStaff() {
    return ApiResponse.success("Lấy danh sách nhân viên thành công (từ DB)",staffService.getAllStaff());
  }

  @GetMapping("/{id}")
  public ApiResponse<StaffAccountResponse> getStaff(@PathVariable UUID id) {
    return ApiResponse.success("Get staff by ID success",staffService.getStaffById(id));
  }

  @PostMapping()
  public ApiResponse<StaffDTO> addStaff(@RequestBody CreateStaffRequest newStaff) {
    return ApiResponse.success("Create staff success",staffService.addStaff(newStaff));
  }

  @DeleteMapping("/{id}")
  public ApiResponse<String> deleteStaffById(@PathVariable UUID id) {
    return ApiResponse.success("Delete staff success",staffService.deleteStaffIfNoAppointments(id));
  }

  @PutMapping("/{id}")
  public ApiResponse<StaffDTO> updateStaff(@PathVariable UUID id, @RequestBody StaffDTO staffDTO) {
    return ApiResponse.success("Update staff success",staffService.updateStaff(staffDTO, id));
  }
}
