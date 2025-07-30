package com.example.Makeup.controller.api.admin;

import com.example.Makeup.dto.model.StaffDTO;
import com.example.Makeup.dto.request.CreateStaffRequest;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.service.IStaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/staffs")
public class StaffAdminRestController {

    private final IStaffService staffService;

    @GetMapping("")
    public ApiResponse<List<StaffDTO>> getAllStaff() {
        return staffService.getAllStaff();
    }

    @GetMapping("/{id}")
    public ApiResponse<StaffDTO> getStaffById(@PathVariable UUID staffId) {
        return staffService.getStaffById(staffId);
    }

    @PostMapping("/create")
    public ApiResponse<StaffDTO> addStaff(@RequestBody CreateStaffRequest newStaff) {
        return  staffService.addStaff(newStaff);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteStaffById(@PathVariable UUID id) {
        return staffService.deleteStaffIfNoAppointments(id);
    }

    @PutMapping("/{id}")
    public ApiResponse<StaffDTO> updateStaff(@PathVariable UUID id, @RequestBody StaffDTO staffDTO) {
        return staffService.updateStaff(staffDTO, id);
    }
}
