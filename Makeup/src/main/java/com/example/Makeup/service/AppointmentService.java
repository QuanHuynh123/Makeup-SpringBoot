package com.example.Makeup.service;

import com.example.Makeup.dto.model.AppointmentDTO;
import com.example.Makeup.dto.model.WeekAppointmentsDTO;
import com.example.Makeup.dto.request.AppointmentRequestDTO;
import com.example.Makeup.entity.Appointment;
import com.example.Makeup.entity.ServiceMakeup;
import com.example.Makeup.entity.Staff;
import com.example.Makeup.entity.User;
import com.example.Makeup.enums.ApiResponse;
import com.example.Makeup.enums.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.AppointmentMapper;
import com.example.Makeup.repository.AppointmentRepository;
import com.example.Makeup.repository.ServiceMakeupRepository;
import com.example.Makeup.repository.StaffRepository;
import com.example.Makeup.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

import static io.lettuce.core.LettuceVersion.getName;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final ServiceMakeupRepository serviceMakeupRepository;
    private final AppointmentMapper appointmentMapper;

    public ApiResponse<List<WeekAppointmentsDTO>> getAppointmentsByMonth(int month, int year, UUID staffID) {
        List<AppointmentDTO> appointments;

        if (staffID != null)
            appointments = appointmentRepository.findAppointmentsByMonthAndStaff(month, year, staffID);
        else
            appointments = appointmentRepository.findAppointmentsByMonth(month, year);

        return ApiResponse.<List<WeekAppointmentsDTO>>builder()
                .code(200)
                .message("Lấy danh sách lịch hẹn thành công!")
                .result(groupAppointmentsByWeek(appointments, month, year))
                .build();
    }

    private List<WeekAppointmentsDTO> groupAppointmentsByWeek(List<AppointmentDTO> appointments, int month, int year) {
        Map<Integer, List<AppointmentDTO>> weeklyAppointments = appointments.stream()
                .collect(Collectors.groupingBy(a -> getWeekOfMonth(a.getMakeupDate())));

        int totalWeeks = getWeeksInMonth(month, year);
        List<WeekAppointmentsDTO> weekAppointmentsDTOList = new ArrayList<>();

        for (int week = 1; week <= totalWeeks; week++) {
            List<AppointmentDTO> weekAppointments = weeklyAppointments.getOrDefault(week, new ArrayList<>());
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
        return new Date(cal.getTimeInMillis());
    }

    // Tính tuần trong tháng dựa trên ngày
    private int getWeekOfMonth(LocalDateTime date) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfMonth());
    }

    private int getWeeksInMonth(int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return (int) Math.ceil(daysInMonth / 7.0);
    }


    public ApiResponse<List<AppointmentDTO>> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        if (appointments.isEmpty()) {
            throw new AppException(ErrorCode.IS_EMPTY);
        }
        return ApiResponse.<List<AppointmentDTO>>builder()
                .code(200)
                .message("Lấy danh sách lịch hẹn thành công!")
                .result(appointments.stream()
                        .map(appointmentMapper::toAppointmentDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public ApiResponse<AppointmentDTO> getAppointmentById(UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new AppException(ErrorCode.CANT_FOUND));

        return ApiResponse.<AppointmentDTO>builder()
                .code(200)
                .message("Lấy lịch hẹn thành công!")
                .result(appointmentMapper.toAppointmentDTO(appointment))
                .build();
    }

//    // Thêm mới một cuộc hẹn
//    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
//        Appointment appointment = appointmentMapper.toAppointmentEntity(appointmentDTO);
//        Appointment savedAppointment = appointmentRepository.save(appointment);
//        return appointmentMapper.toAppointmentDTO(savedAppointment);
//    }

    public ApiResponse<AppointmentDTO> updateAppointment(UUID idAppointment, AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentRepository.findById(idAppointment)
                .orElseThrow(() -> new AppException(ErrorCode.CANT_FOUND));

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

        ServiceMakeup serviceMakeup = serviceMakeupRepository.findById(appointmentDTO.getServiceMakeupId())
                .orElseThrow(() -> new RuntimeException("Service Makeup not found with ID: " + appointmentDTO.getServiceMakeupId()));
        appointment.setServiceMakeup(serviceMakeup);

        Staff staff = staffRepository.findById(appointmentDTO.getStaffId())
                .orElseThrow(() -> new RuntimeException("Staff not found with ID: " + appointmentDTO.getStaffId()));
        appointment.setStaff(staff);

        // Lưu cuộc hẹn đã cập nhật
        Appointment updatedAppointment = appointmentRepository.save(appointment);

        // Chuyển đổi sang DTO
        AppointmentDTO updatedAppointmentDTO = appointmentMapper.toAppointmentDTO(updatedAppointment);

        // Trả về phản hồi thành công
        return ApiResponse.<AppointmentDTO>builder()
                .code(200)  // Mã phản hồi HTTP cho thành công
                .message("Cập nhật cuộc hẹn thành công!")
                .result(updatedAppointmentDTO)
                .build();
    }

    @Transactional
    public ApiResponse<Boolean> deleteAppointment(UUID appointmentId) {
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new AppException(ErrorCode.CANT_FOUND);
        }
        appointmentRepository.deleteById(appointmentId);
        return ApiResponse.<Boolean>builder()
                .code(200)
                .message("Xóa lịch hẹn thành công!")
                .result(true)
                .build();
    }

    @Transactional
    public ApiResponse<AppointmentDTO> createAppointment(AppointmentRequestDTO newAppointment) {

        Appointment appointment = new Appointment();
        appointment.setStartTime(newAppointment.getStartTime());
        appointment.setEndTime(newAppointment.getEndTime());
        appointment.setMakeupDate(newAppointment.getMakeupDate());
        Staff staff = staffRepository.findById(newAppointment.getStaffId()).orElseThrow(() -> new AppException(ErrorCode.CANT_FOUND));
        appointment.setStaff(staff);
        ServiceMakeup serviceMakeup =  serviceMakeupRepository.findById(newAppointment.getServiceMakeupId())
                .orElseThrow(() -> new AppException(ErrorCode.CANT_FOUND));
        appointment.setServiceMakeup(serviceMakeup);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user ;
        if (username == null || username.isEmpty() || "anonymousUser".equals(username) ) {
            // If user is not logged in, create a guest user
            user = new User();
            user.setFullName(newAppointment.getGuestInfo().getFullName());
            user.setEmail(newAppointment.getGuestInfo().getEmail());
            user.setPhone(newAppointment.getGuestInfo().getPhone());
            user.setAddress("Chưa có địa chỉ");
            user.setGuest(true);
            user.setGuestToken(UUID.randomUUID().toString());
            user = userRepository.save(user);
        } else {
            // Get user is logged in
            user = userRepository.findByAccount_userName(username)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        }

        appointment.setUser(user);

        // Check conflict with existing appointments
        List<Appointment> conflictingAppointments = appointmentRepository.findConflictingAppointments(
                appointment.getStaff().getId(),
                appointment.getMakeupDate(),
                appointment.getStartTime(),
                appointment.getEndTime()
        );
        if (!conflictingAppointments.isEmpty()) {
            throw new AppException(ErrorCode.STAFF_ALREADY_BOOKED);
        }

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return ApiResponse.<AppointmentDTO>builder()
                .code(200)
                .message("Add appointment success!")
                .result(appointmentMapper.toAppointmentDTO(savedAppointment))
                .build();
    }

    public ApiResponse<List<AppointmentDTO>> getAllAppointmentsDetail() {
        List<AppointmentDTO> appointments = appointmentRepository.findAllAppointmentsWithDetails();
        if (appointments.isEmpty()) {
            throw new AppException(ErrorCode.IS_EMPTY);
            }
        return ApiResponse.<List<AppointmentDTO>>builder()
                .code(200)
                .message("Lấy danh sách lịch hẹn thành công!")
                .result(appointments)
                .build();
    }

    public ApiResponse<AppointmentDTO> getAppointmentDetailById(UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppException(ErrorCode.CANT_FOUND));
        return ApiResponse.<AppointmentDTO>builder()
                .code(200)
                .message("Lấy chi tiết lịch hẹn thành công!")
                .result(appointmentMapper.toAppointmentDTO(appointment))
                .build();
    }

    private List<AppointmentDTO> getAppointmentUser(UUID userId){
        List<Appointment> appointments = appointmentRepository.findAllByUserId(userId);
        return appointments.stream()
                .map(appointmentMapper::toAppointmentDTO)
                .collect(Collectors.toList());
    }

    public ApiResponse<List<AppointmentDTO>> getAppointmentsByDateAndStaff(UUID staffId, Date makeupDate) {
        List<Appointment> appointments = appointmentRepository.findAppointmentsByDateAndStaff(staffId, makeupDate);
        if (appointments.isEmpty()) {
            throw new AppException(ErrorCode.IS_EMPTY);
        }
        return ApiResponse.<List<AppointmentDTO>>builder()
                .code(200)
                .message("Lấy danh sách lịch hẹn thành công!")
                .result(appointments.stream()
                        .map(appointmentMapper::toAppointmentDTO)
                        .collect(Collectors.toList()))
                .build();
    }

}