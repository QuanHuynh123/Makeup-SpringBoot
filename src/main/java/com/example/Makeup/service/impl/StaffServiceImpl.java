package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.StaffDTO;
import com.example.Makeup.dto.request.CreateStaffRequest;
import com.example.Makeup.dto.response.StaffAccountResponse;
import com.example.Makeup.entity.Account;
import com.example.Makeup.entity.Appointment;
import com.example.Makeup.entity.Role;
import com.example.Makeup.entity.Staff;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.mapper.StaffMapper;
import com.example.Makeup.repository.AccountRepository;
import com.example.Makeup.repository.AppointmentRepository;
import com.example.Makeup.repository.RoleRepository;
import com.example.Makeup.repository.StaffRepository;
import com.example.Makeup.service.IStaffService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements IStaffService {

  private final StaffRepository staffRepository;
  private final AppointmentRepository appointmentRepository;
  private final StaffMapper staffMapper;
  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  private final RoleRepository roleRepository;

  @Override
  public List<StaffDTO> getAllStaff() {
    return staffRepository.findAll()
            .stream()
            .map(staffMapper::toStaffDTO)
            .collect(Collectors.toList());
  }

  @Override
    @Transactional(readOnly = true)
  public StaffAccountResponse getStaffById(UUID staffId) {
    Staff staff =
      staffRepository.findById(staffId).orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));

    Account account = staff.getAccount();
    String accountId = account != null && account.getId() != null ? account.getId().toString() : null;
    String roleName =
      account != null && account.getRole() != null ? account.getRole().getNameRole() : "UNKNOWN";
    String userName = account != null ? account.getUserName() : null;
    String createdAt =
      account != null && account.getCreatedAt() != null ? account.getCreatedAt().toString() : null;
    String updatedAt =
      account != null && account.getUpdatedAt() != null ? account.getUpdatedAt().toString() : null;

    return new StaffAccountResponse(
      staff.getId().toString(),
      staff.getNameStaff(),
      staff.getPhone(),
      accountId,
      roleName,
      userName,
      createdAt,
      updatedAt);
  }

  @Override
  public StaffDTO addStaff(CreateStaffRequest newStaff) {

    if (accountRepository.existsByUserName(newStaff.getUserName()))
      throw new AppException(ErrorCode.USER_ALREADY_EXISTED);

    String encodedPassword = passwordEncoder.encode(newStaff.getPassword());
    Staff createStaff = new Staff(null, newStaff.getNameStaff(), newStaff.getPhone(), null, null);

    // Create AccountDTO
    Account account = new Account();
    account.setUserName(newStaff.getUserName());
    account.setPassWord(encodedPassword);

    Role role =
        roleRepository
            .findById(newStaff.getRole())
            .orElseThrow(() -> new AppException(ErrorCode.COMMON_RESOURCE_NOT_FOUND));
    account.setRole(role);

    Account savedAccount = accountRepository.save(account);

    createStaff.setAccount(savedAccount);

    return staffMapper.toStaffDTO(staffRepository.save(createStaff));
  }

  @Override
  public String deleteStaffIfNoAppointments(UUID staffId) {
    Staff staff =
        staffRepository
            .findById(staffId)
            .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));

    // Kiểm tra xem có lịch hẹn không
    List<Appointment> appointments = appointmentRepository.findByStaffId(staffId);
    if (!appointments.isEmpty()) {
      throw new AppException(ErrorCode.STAFF_HAS_APPOINTMENTS);
    }

    staffRepository.delete(staff);
    return "Staff with ID " + staffId + " has been deleted.";
  }

  @Override
  public StaffDTO getStaffDetailById(UUID staffId) {
    StaffDTO staffDetailDTO = staffRepository.findStaffDetailById(staffId);
    if (staffDetailDTO == null) {
      throw new AppException(ErrorCode.STAFF_NOT_FOUND);
    }

    return  staffDetailDTO;
  }

  @Override
  @Transactional
  public StaffDTO updateStaff(StaffDTO staffDTO, UUID staffId) {

    Staff staff =
        staffRepository
            .findById(staffId)
            .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));

    staff.setNameStaff(staffDTO.getNameStaff());
    staff.setPhone(staffDTO.getPhone());
    staffRepository.save(staff);

    return  staffMapper.toStaffDTO(staff);
  }
}
