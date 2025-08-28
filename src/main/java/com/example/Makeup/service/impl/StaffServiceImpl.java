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
import com.example.Makeup.utils.RedisStatusManager;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaffServiceImpl implements IStaffService {

  private static final String STAFF_CACHE_KEY = "staffs";
  private final StaffRepository staffRepository;
  private final AppointmentRepository appointmentRepository;
  private final StaffMapper staffMapper;
  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  private final RoleRepository roleRepository;
  private final RedisTemplate<String, Object> redisTemplate;

  @Override
  public List<StaffDTO> getAllStaff() {
    if (RedisStatusManager.isRedisAvailable()) {
      try {
        Object cached = redisTemplate.opsForValue().get(STAFF_CACHE_KEY);
        if (cached != null) {
          List<StaffDTO> staffList = (List<StaffDTO>) cached;
          log.info("Lấy danh sách nhân viên thành công (từ Redis)");
          return staffList;
        } else {
          log.info("Cache staff not found, fetching from DB");
        }
      } catch (RedisConnectionFailureException e) {
        log.warn("⚠️ Redis connection failed: {}", e.getMessage());
        RedisStatusManager.setRedisAvailable(false);
      } catch (Exception e) {
        log.warn("⚠️ Redis GET failed, fallback to DB: {}", e.getMessage());
      }
    }

    List<Staff> staffList = staffRepository.findAll();
    return staffList.stream().map(staffMapper::toStaffDTO).collect(Collectors.toList());
  }

  @Override
  public StaffAccountResponse getStaffById(UUID staffId) {
    System.out.println("Fetching staff by ID: " + staffId);
      return staffRepository
          .findStaffAccountById(staffId)
          .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));
  }

  @Override
  public StaffDTO addStaff(CreateStaffRequest newStaff) {

    if (accountRepository.existsByUserName(newStaff.getUserName()))
      throw new AppException(ErrorCode.USER_ALREADY_EXISTED);

    String encodedPassword = passwordEncoder.encode(newStaff.getPassword());
    Staff createStaff = new Staff(null, newStaff.getNameStaff(), newStaff.getPhone(), null, null);

    // Create AccountDTO
    Account account = new Account();
    account.setUserName(createStaff.getNameStaff());
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
