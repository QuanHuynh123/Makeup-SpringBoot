package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.AppointmentDTO;
import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.dto.response.WeekAppointmentsDTO;
import com.example.Makeup.dto.request.AppointmentRequest;
import com.example.Makeup.dto.response.AppointmentsAdminResponse;
import com.example.Makeup.entity.Appointment;
import com.example.Makeup.entity.Staff;
import com.example.Makeup.entity.TypeMakeup;
import com.example.Makeup.entity.User;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.AppointmentMapper;
import com.example.Makeup.repository.AppointmentRepository;
import com.example.Makeup.repository.TypeMakeupRepository;
import com.example.Makeup.repository.StaffRepository;
import com.example.Makeup.repository.UserRepository;
import com.example.Makeup.service.IAppointmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements IAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final TypeMakeupRepository typeMakeupRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public ApiResponse<List<WeekAppointmentsDTO>> getAppointmentsByMonth(int month, int year, UUID staffID) {
        List<AppointmentsAdminResponse> appointments =  appointmentRepository.findAppointmentsByMonth(month, year, staffID);
        if (appointments.isEmpty()) {
            appointments = new ArrayList<>();
        }
        return ApiResponse.success("Get appointments by month success", groupAppointmentsByWeek(appointments, month, year));
    }

    @Override
    public ApiResponse<List<AppointmentsAdminResponse>> getAllAppointments() {
        List<AppointmentsAdminResponse> appointments = appointmentRepository.findAllAppointments();
        if (appointments.isEmpty()) {
            throw new AppException(ErrorCode.COMMON_IS_EMPTY);
        }
        return ApiResponse.success("Get all appointments success", appointments);
    }

    @Override
    public ApiResponse<AppointmentDTO> getAppointmentById(UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new AppException(ErrorCode.COMMON_RESOURCE_NOT_FOUND));

        return ApiResponse.success("Get appointment by ID success", appointmentMapper.toAppointmentDTO(appointment));
    }

    @Override
    public ApiResponse<List<AppointmentsAdminResponse>> getAppointmentByUserId(UUID userId) {
        List<AppointmentsAdminResponse> appointment = appointmentRepository.findAllByUserId(userId);
        return ApiResponse.success("Get appointment by ID success", appointment);
    }

    @Override
    public ApiResponse<AppointmentDTO> updateAppointment(UUID idAppointment, AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentRepository.findById(idAppointment)
                .orElseThrow(() -> new AppException(ErrorCode.COMMON_RESOURCE_NOT_FOUND));

        AppointmentDTO asd = appointmentMapper.toAppointmentDTO(appointment);

        // Kiểm tra xem status có thay đổi từ 0 thành 1 không
        boolean isStatusChangedToActive = asd.isStatus() == false && appointmentDTO.isStatus() == true;

        // Nếu status thay đổi từ 0 thành 1, kiểm tra có bị trùng lịch với nhân viên không
        if (isStatusChangedToActive) {
            List<Appointment> conflictingAppointments = appointmentRepository.findConflictingAppointments(
                    appointmentDTO.getStaffId(),
                    appointmentDTO.getMakeupDate(),
                    appointmentDTO.getStartTime(),
                    appointmentDTO.getEndTime()
            );

            // Nếu có cuộc hẹn xung đột
            if (!conflictingAppointments.isEmpty()) {
                throw new RuntimeException("Có cuộc hẹn xung đột với nhân viên vào cùng thời gian này!");
            }
        }

        // Cập nhật các thuộc tính từ appointmentDTO
        appointment.setStartTime(appointmentDTO.getStartTime());
        appointment.setEndTime(appointmentDTO.getEndTime());
        appointment.setMakeupDate(appointmentDTO.getMakeupDate());
        appointment.setStatus(appointmentDTO.isStatus());

        User user = userRepository.findById(appointmentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + appointmentDTO.getUserId()));
        appointment.setUser(user);

        TypeMakeup typeMakeup = typeMakeupRepository.findById(appointmentDTO.getTypeMakeupId())
                .orElseThrow(() -> new RuntimeException("Service Makeup not found with ID: " + appointmentDTO.getTypeMakeupId()));
        appointment.setTypeMakeup(typeMakeup);

        Staff staff = staffRepository.findById(appointmentDTO.getStaffId())
                .orElseThrow(() -> new RuntimeException("Staff not found with ID: " + appointmentDTO.getStaffId()));
        appointment.setStaff(staff);

        // Lưu cuộc hẹn đã cập nhật
        Appointment updatedAppointment = appointmentRepository.save(appointment);

        // Chuyển đổi sang DTO
        AppointmentDTO updatedAppointmentDTO = appointmentMapper.toAppointmentDTO(updatedAppointment);

        // Trả về phản hồi thành công
        return ApiResponse.success("Update appointment success", updatedAppointmentDTO);
    }

    @Override
    @Transactional
    public ApiResponse<Boolean> deleteAppointment(UUID appointmentId) {
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new AppException(ErrorCode.APPOINTMENT_NOT_FOUND);
        }
        appointmentRepository.deleteById(appointmentId);
        return ApiResponse.success("Delete appointment success", true);
    }

    @Override
    @Transactional
    public ApiResponse<AppointmentDTO> createAppointment(AppointmentRequest newAppointment) {

        Appointment appointment = new Appointment();
        appointment.setStartTime(newAppointment.getStartTime());
        appointment.setEndTime(newAppointment.getEndTime());
        appointment.setMakeupDate(LocalDate.from(newAppointment.getMakeupDate()));

        Staff staff = staffRepository.findById(newAppointment.getStaffId())
                .orElseThrow(() -> new AppException(ErrorCode.STAFF_NOT_FOUND));
        appointment.setStaff(staff);

        TypeMakeup typeMakeup = typeMakeupRepository.findById(newAppointment.getTypeMakeupId())
                .orElseThrow(() -> new AppException(ErrorCode.COMMON_RESOURCE_NOT_FOUND));
        appointment.setTypeMakeup(typeMakeup);
        appointment.setPrice(typeMakeup.getPrice());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDTO userDTO = null;
        if (principal instanceof UserDTO) {
            userDTO = (UserDTO) principal;
        }
        User user;

        if (userDTO == null) {
            user = new User();
            user.setFullName(newAppointment.getGuestInfo().getFullName());
            user.setEmail(newAppointment.getGuestInfo().getEmail());
            user.setPhone(newAppointment.getGuestInfo().getPhone());
            user.setAddress("Chưa có địa chỉ");
            user.setGuest(true);
            user.setGuestToken(UUID.randomUUID().toString());
            user = userRepository.save(user);
        } else {
            user = userRepository.findByAccountId(userDTO.getAccountId())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        }

        appointment.setUser(user);

        // ✅ Check conflict
        checkAppointmentConflict(appointment);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return ApiResponse.success("Create appointment success", appointmentMapper.toAppointmentDTO(savedAppointment));
    }

    private void checkAppointmentConflict(Appointment appointment) {
        List<Appointment> conflicts = appointmentRepository.findConflictingAppointments(
                appointment.getStaff().getId(),
                appointment.getMakeupDate(),
                appointment.getStartTime(),
                appointment.getEndTime()
        );
        if (!conflicts.isEmpty()) {
            throw new AppException(ErrorCode.STAFF_ALREADY_BOOKED);
        }
    }

    @Override
    public ApiResponse<List<AppointmentDTO>> getAppointmentsByDateAndStaff(UUID staffId, LocalDate makeupDate) {
        List<Appointment> appointments = appointmentRepository.findAppointmentsByDateAndStaff(staffId, makeupDate);
        if (appointments.isEmpty()) {
            throw new AppException(ErrorCode.APPOINTMENT_IS_EMPTY);
        }

        List<AppointmentDTO> appointmentDTOs = appointments.stream()
                .map(appointmentMapper::toAppointmentDTO)
                .collect(Collectors.toList());

        return ApiResponse.success("Get appointments success", appointmentDTOs);

    }

    private List<WeekAppointmentsDTO> groupAppointmentsByWeek(List<AppointmentsAdminResponse> appointments, int month, int year) {
        Map<Integer, List<AppointmentsAdminResponse>> weeklyAppointments = new HashMap<>();
        if (appointments != null) {
            weeklyAppointments = appointments.stream()
                    .collect(Collectors.groupingBy(a -> getWeekOfMonth(a.getMakeupDate())));
        }

        int totalWeeks = getWeeksInMonth(month, year);
        List<WeekAppointmentsDTO> weekAppointmentsDTOList = new ArrayList<>();

        for (int week = 1; week <= totalWeeks; week++) {
            List<AppointmentsAdminResponse> weekAppointments = weeklyAppointments.getOrDefault(week, new ArrayList<>());
            Date startDate = getWeekStartDate(week, month, year);
            Date endDate = getWeekEndDate(week, month, year);

            weekAppointmentsDTOList.add(new WeekAppointmentsDTO(week, startDate, endDate, weekAppointments));
        }

        return weekAppointmentsDTOList;
    }

    private Date getWeekStartDate(int week, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        cal.add(Calendar.DAY_OF_MONTH, (week - 1) * 7);

        if (cal.get(Calendar.MONTH) != month - 1) {
            cal.set(year, month - 1, 1);
        }
        return new Date(cal.getTimeInMillis());
    }

    private Date getWeekEndDate(int week, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        cal.add(Calendar.DAY_OF_MONTH, (week - 1) * 7 + 6);

        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (cal.get(Calendar.DAY_OF_MONTH) > maxDay) {
            cal.set(Calendar.DAY_OF_MONTH, maxDay);
        }

        if (cal.get(Calendar.MONTH) != month - 1) {
            cal.set(year, month - 1, maxDay);
        }

        return new Date(cal.getTimeInMillis());
    }

    // Get 1 week of month
    private int getWeekOfMonth(LocalDate date) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfMonth());
    }

    // Get total weeks in month
    private int getWeeksInMonth(int month, int year) {
        LocalDate firstDay = LocalDate.of(year, month, 1);
        LocalDate lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth());
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        int lastWeek = lastDay.get(weekFields.weekOfMonth());
        // If the last day of the month is not a Sunday, we need to add one more week
        if (lastDay.getDayOfWeek().getValue() < 7) {
            return lastWeek;
        }
        return lastWeek + 1;
    }


}