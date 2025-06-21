package com.example.Makeup.service;
import com.example.Makeup.dto.request.CreateStaffRequest;
import com.example.Makeup.enums.ApiResponse;
import com.example.Makeup.dto.model.StaffDTO;
import com.example.Makeup.entity.Account;
import com.example.Makeup.entity.Appointment;
import com.example.Makeup.entity.Role;
import com.example.Makeup.entity.Staff;
import com.example.Makeup.enums.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.StaffMapper;
import com.example.Makeup.repository.AccountRepository;
import com.example.Makeup.repository.AppointmentRepository;
import com.example.Makeup.repository.RoleRepository;
import com.example.Makeup.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;
    private final AppointmentRepository appointmentRepository;
    private final StaffMapper staffMapper;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final RoleRepository roleRepository;


    public ApiResponse<List<StaffDTO>> getAllStaff() {

        List<Staff> staffList = staffRepository.findAll();
        if (staffList.isEmpty()) {
            throw new AppException(ErrorCode.IS_EMPTY);
        }

        return ApiResponse.<List<StaffDTO>>builder()
                .code(200)
                .message("Get all staff success")
                .result(staffList.stream()
                        .map(staffMapper::toStaffDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public ApiResponse<List<StaffDTO>> getAllStaffDetail() {
        List<StaffDTO> staffDetailList =  null ;
        //staffRepository.findAllStaff();
        if (staffDetailList.isEmpty()) {
            throw new AppException(ErrorCode.IS_EMPTY);
        }
        return ApiResponse.<List<StaffDTO>>builder()
                .code(200)
                .message("Get all staff details success")
                .result(staffDetailList)
                .build();
    }

    public ApiResponse<StaffDTO> getStaffById(UUID staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new AppException(ErrorCode.CANT_FOUND));
        return ApiResponse.<StaffDTO>builder()
                .code(200)
                .message("Get staff by ID success")
                .result(staffMapper.toStaffDTO(staff))
                .build();
    }

    public ApiResponse<StaffDTO>  addStaff(CreateStaffRequest newStaff) {

        if (accountRepository.existsByUserName(newStaff.getUserName())) {
            return ApiResponse.<StaffDTO>builder()
                    .code(400)
                    .message(ErrorCode.USER_ALREADY_EXISTED.getMessage())
                    .build();
        }

        String encodedPassword = passwordEncoder.encode(newStaff.getPassword());
        Staff createStaff = new Staff(null, newStaff.getNameStaff(), newStaff.getPhone(), null, null);

        //Create AccountDTO
        Account account = new Account();
        account.setUserName(createStaff.getNameStaff());
        account.setPassWord(encodedPassword);

        Role role = roleRepository.findById(newStaff.getRole())
                .orElseThrow(() -> new AppException(ErrorCode.CANT_FOUND));
        account.setRole(role);

        Account savedAccount =  accountRepository.save(account);

        createStaff.setAccount(savedAccount);

        return ApiResponse.<StaffDTO>builder()
                .code(200)
                .message("Create staff success")
                .result(staffMapper.toStaffDTO(staffRepository.save(createStaff)))
                .build();
    }

    public ApiResponse<String> deleteStaffIfNoAppointments(UUID staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new AppException(ErrorCode.CANT_FOUND));

        // Kiểm tra xem có lịch hẹn không
        List<Appointment> appointments = appointmentRepository.findByStaffId(staffId);
        if (!appointments.isEmpty()) {
            throw new AppException(ErrorCode.STAFF_HAS_APPOINTMENTS);
        }

        staffRepository.delete(staff);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Delete staff success")
                .result("Staff with ID " + staffId + " has been deleted.")
                .build();
    }


    public ApiResponse<StaffDTO> getStaffDetailById(UUID staffId) {
        StaffDTO staffDetailDTO = staffRepository.findStaffDetailById(staffId);
        if (staffDetailDTO == null) {
            throw new AppException(ErrorCode.CANT_FOUND);
        }

        return ApiResponse.<StaffDTO>builder()
                .code(200)
                .message("Get staff detail by ID success")
                .result(staffDetailDTO)
                .build();
    }

    @Transactional
    public ApiResponse<StaffDTO> updateStaff(StaffDTO staffDTO) {

        Staff staff = staffRepository.findById(staffDTO.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CANT_FOUND));


        staff.setNameStaff(staffDTO.getNameStaff());
        staff.setPhone(staffDTO.getPhone());
        staffRepository.save(staff);

        return ApiResponse.<StaffDTO>builder()
                .code(200)
                .message("Update staff success")
                .result(staffMapper.toStaffDTO(staffRepository.save(staff)))
                .build();
    }

}