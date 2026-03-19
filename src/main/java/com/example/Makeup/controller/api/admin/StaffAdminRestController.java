package com.example.Makeup.controller.api.admin;

import com.example.Makeup.dto.model.StaffDTO;
import com.example.Makeup.dto.request.CreateStaffRequest;
import com.example.Makeup.dto.response.StaffAccountResponse;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IStaffService;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Staff Admin API", description = "API for admin staff operations")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/staffs")
public class StaffAdminRestController {

  private final IStaffService staffService;

  @Operation(summary = "Get all staff", description = "Retrieve a list of all staff members")
  @GetMapping()
  public ApiResponse<List<StaffDTO>> getAllStaff() {
    return ApiResponse.success("Lấy danh sách nhân viên thành công (từ DB)",staffService.getAllStaff());
  }

  @Operation(summary = "Get staff by ID", description = "Retrieve staff details by staff ID")
  @GetMapping("/{id}")
  public ApiResponse<StaffAccountResponse> getStaff(@PathVariable UUID id) {
    return ApiResponse.success("Get staff by ID success",staffService.getStaffById(id));
  }

  @Operation(summary = "Add new staff", description = "Add a new staff member with the provided details")
  @PostMapping()
  public ApiResponse<StaffDTO> addStaff(@RequestBody CreateStaffRequest newStaff) {
    return ApiResponse.success("Create staff success",staffService.addStaff(newStaff));
  }

  @Operation(summary = "Delete staff", description = "Delete a staff member by staff ID if they have no appointments")
  @DeleteMapping("/{id}")
  public ApiResponse<String> deleteStaffById(@PathVariable UUID id) {
    return ApiResponse.success("Delete staff success",staffService.deleteStaffIfNoAppointments(id));
  }

  @Operation(summary = "Update staff", description = "Update an existing staff member by staff ID")
  @PatchMapping("/{id}")
  public ApiResponse<StaffDTO> updateStaff(@PathVariable UUID id, @RequestBody StaffDTO staffDTO) {
    return ApiResponse.success("Update staff success",staffService.updateStaff(staffDTO, id));
  }
}
