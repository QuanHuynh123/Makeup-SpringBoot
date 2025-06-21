package com.example.Makeup.controller.api.web;

import com.example.Makeup.dto.model.StaffDTO;
import com.example.Makeup.dto.request.CreateStaffRequest;
import com.example.Makeup.enums.ApiResponse;
import com.example.Makeup.service.StaffService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/staff")
public class StaffRestController {
    private final  StaffService staffService;

    public StaffRestController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public ApiResponse<List<StaffDTO>> getAllStaff() {
        ApiResponse<List<StaffDTO>> staffList = staffService.getAllStaff();
        return staffList;
    }

    @GetMapping("/staffDetail")
    public ApiResponse<List<StaffDTO>> getAllStaffDetail() {
        return staffService.getAllStaffDetail();
    }

    @GetMapping("/staffDetail/{staffId}")
    public ApiResponse<StaffDTO> getStaffDetailById(@PathVariable UUID staffId) {
        return staffService.getStaffDetailById(staffId);
    }

    @GetMapping("/{id}")
    public ApiResponse<StaffDTO> getStaffById(@PathVariable UUID staffId) {
        return staffService.getStaffById(staffId);
    }

    @PostMapping("/staffDetail")
    public ApiResponse<StaffDTO> addStaff(@RequestBody CreateStaffRequest newStaff) {
        return  staffService.addStaff(newStaff);
    }


    @PutMapping("/staffDetail/{id}")
    public ApiResponse<StaffDTO> updateStaff(@PathVariable int id, @RequestBody StaffDTO staffDTO) {
        ApiResponse<StaffDTO> updatedStaff = staffService.updateStaff(staffDTO);
        return updatedStaff;
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<StaffDTO> updateStaff(@PathVariable int id, @RequestBody StaffDTO staffDTO) {
//        StaffDTO updatedStaff = staffService.updateStaff(id, staffDTO);
//        return ResponseEntity.ok(updatedStaff);
//    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteStaff(@PathVariable UUID staffId) {
        return staffService.deleteStaffIfNoAppointments(staffId);
    }

}