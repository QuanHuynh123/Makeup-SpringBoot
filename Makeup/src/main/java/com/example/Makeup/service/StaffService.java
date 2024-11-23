package com.example.Makeup.service;

import com.example.Makeup.dto.StaffDTO;
import com.example.Makeup.entity.Account;
import com.example.Makeup.entity.Staff;
import com.example.Makeup.mapper.StaffMapper;
import com.example.Makeup.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StaffMapper staffMapper;

    public List<StaffDTO> getAllStaff() {
        // Sử dụng mapper để chuyển đổi danh sách Staff sang StaffDTO
        List<Staff> staffList = staffRepository.findAll();
        return staffMapper.toStaffDTOList(staffList); // Chuyển đổi danh sách
    }

    public Optional<Staff> getStaffById(int id) {
        return staffRepository.findById(id);
    }

    public Staff addStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    public Staff updateStaff(int id, Staff staffDetails) {
        return staffRepository.findById(id).map(staff -> {
            staff.setNameStaff(staffDetails.getNameStaff());
            staff.setPhone(staffDetails.getPhone());
            return staffRepository.save(staff);
        }).orElseThrow(() -> new RuntimeException("Staff not found with id " + id));
    }

    public void deleteStaff(int id) {
        staffRepository.deleteById(id);
    }
}
