package com.example.Makeup.service;

import com.example.Makeup.dto.ApiResponse;
import com.example.Makeup.dto.AppointmentDTO;
import com.example.Makeup.dto.AppointmentDetailDTO;
import com.example.Makeup.dto.WeekAppointmentsDTO;
import com.example.Makeup.entity.Appointment;
import com.example.Makeup.entity.ServiceMakeup;
import com.example.Makeup.entity.Staff;
import com.example.Makeup.entity.User;
import com.example.Makeup.mapper.AppointmentMapper;
import com.example.Makeup.repository.AppointmentRepository;
import com.example.Makeup.repository.ServiceMakeupRepository;
import com.example.Makeup.repository.StaffRepository;
import com.example.Makeup.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private ServiceMakeupRepository serviceMakeupRepository;
    @Autowired
    private AppointmentMapper appointmentMapper;

    public List<WeekAppointmentsDTO> getAppointmentsByMonth(int month, int year, Integer staffID) {
        List<AppointmentDetailDTO> appointments;

        // Kiểm tra nếu có `staffID`, gọi phương thức phù hợp trong repository
        if (staffID != null) {
            appointments = appointmentRepository.findAppointmentsByMonthAndStaff(month, year, staffID);
        } else {
            appointments = appointmentRepository.findAppointmentsByMonth(month, year);
        }

        return groupAppointmentsByWeek(appointments, month, year);
    }


    // Chuyển đổi từ entity Appointment sang AppointmentDTO
    private AppointmentDTO convertToDTO(Appointment appointment) {
        return new AppointmentDTO(
                appointment.getId(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getMakeupDate(),
                appointment.getStatus(),
                appointment.getUser().getId(),
                appointment.getServiceMakeup().getId(),
                appointment.getStaff().getId()
        );
    }

    // Nhóm các lịch hẹn theo tuần trong tháng và thêm thông tin ngày bắt đầu, kết thúc của tuần
    private List<WeekAppointmentsDTO> groupAppointmentsByWeek(List<AppointmentDetailDTO> appointments, int month, int year) {
        Map<Integer, List<AppointmentDetailDTO>> weeklyAppointments = new HashMap<>();

        // Nhóm các lịch hẹn theo tuần trong tháng
        for (AppointmentDetailDTO appointment : appointments) {
            int weekOfMonth = getWeekOfMonth(appointment.getMakeupDate());
            weeklyAppointments
                    .computeIfAbsent(weekOfMonth, k -> new ArrayList<>())
                    .add(appointment);
        }

        // Xác định tổng số tuần trong tháng
        int totalWeeks = getWeeksInMonth(month, year);

        // Trả về danh sách các tuần, bao gồm cả tuần không có lịch hẹn
        List<WeekAppointmentsDTO> weekAppointmentsDTOList = new ArrayList<>();
        for (int weekNumber = 1; weekNumber <= totalWeeks; weekNumber++) {
            List<AppointmentDetailDTO> appointmentsInWeek = weeklyAppointments.getOrDefault(weekNumber, new ArrayList<>());

            // Tính ngày bắt đầu và kết thúc của tuần
            Date startDate = getStartDateOfWeek(weekNumber, month, year);
            Date endDate = getEndDateOfWeek(weekNumber, month, year);

            weekAppointmentsDTOList.add(new WeekAppointmentsDTO(
                    weekNumber,
                    startDate,
                    endDate,
                    appointmentsInWeek
            ));
        }

        return weekAppointmentsDTOList;
    }

    // Tính tuần trong tháng dựa trên ngày
    private int getWeekOfMonth(Date makeupDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(makeupDate);

        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Xác định tuần trong tháng dựa trên ngày trong tháng
        if (dayOfMonth >= 1 && dayOfMonth <= 7) {
            return 1;
        } else if (dayOfMonth >= 8 && dayOfMonth <= 14) {
            return 2;
        } else if (dayOfMonth >= 15 && dayOfMonth <= 21) {
            return 3;
        } else if (dayOfMonth >= 22 && dayOfMonth <= 28) {
            return 4;
        } else {
            return 5; // Tuần cuối trong tháng (29 - 31)
        }
    }

    // Tính ngày bắt đầu của tuần
    private Date getStartDateOfWeek(int weekNumber, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1); // Set to the first day of the month

        // Tuần đầu tiên luôn bắt đầu từ ngày 1
        if (weekNumber == 1) {
            return new Date(calendar.getTimeInMillis());
        }

        // Các tuần sau tính theo thứ tự
        calendar.add(Calendar.DAY_OF_MONTH, (weekNumber - 1) * 7);
        return new Date(calendar.getTimeInMillis());
    }

    // Tính ngày kết thúc của tuần
    private Date getEndDateOfWeek(int weekNumber, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1); // Set to the first day of the month

        // Tuần đầu tiên sẽ kết thúc vào cuối tuần của tháng (7 ngày sau ngày 1)
        if (weekNumber == 1) {
            calendar.add(Calendar.DAY_OF_MONTH, 6);
            return new Date(calendar.getTimeInMillis());
        }

        // Lấy ngày cuối của tháng

        // Các tuần sau kết thúc vào cuối tuần của từng tuần
        calendar.add(Calendar.DAY_OF_MONTH, (weekNumber - 1) * 7 + 6);

//        // Nếu ngày kết thúc của tuần vượt quá ngày cuối của tháng, điều chỉnh lại
//        if (calendar.get(Calendar.DAY_OF_MONTH) > lastDayOfMonth) {
//            calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
//        }

        // Kiểm tra lại nếu ngày kết thúc không phải trong tháng này
        if (calendar.get(Calendar.MONTH) == (month)) {
            calendar.add(Calendar.DAY_OF_MONTH, -((weekNumber - 1) * 7 + 6));
            int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
        }


        return new Date(calendar.getTimeInMillis());
    }


    // Tính tổng số tuần trong tháng
    private int getWeeksInMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return (int) Math.ceil(daysInMonth / 7.0);
    }
    // Lấy tất cả các cuộc hẹn
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toAppointmentDTO)
                .collect(Collectors.toList());
    }
    public List<Appointment> getAllAppointmentsEntity() {
        return appointmentRepository.findAll();
    }
    // Lấy cuộc hẹn theo ID
    public AppointmentDTO getAppointmentById(int id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        return appointment.map(appointmentMapper::toAppointmentDTO)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + id));
    }

    // Thêm mới một cuộc hẹn
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentMapper.toAppointmentEntity(appointmentDTO);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toAppointmentDTO(savedAppointment);
    }

    // Cập nhật một cuộc hẹn
    public ApiResponse<AppointmentDTO> updateAppointment(int id, AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + id));

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

    // Xóa một cuộc hẹn
    public void deleteAppointment(int id) {
        if (!appointmentRepository.existsById(id)) {
            throw new RuntimeException("Appointment not found with ID: " + id);
        }
        appointmentRepository.deleteById(id);
    }

    public AppointmentDTO addAppointment(AppointmentDTO appointmentDTO) {
        // Chuyển đổi DTO sang entity
        Appointment appointment = appointmentMapper.toAppointmentEntity(appointmentDTO);
        // Kiểm tra trùng lịch hẹn
        List<Appointment> conflictingAppointments = appointmentRepository.findConflictingAppointments(
                appointment.getStaff().getId(),
                appointment.getMakeupDate(),
                appointment.getStartTime(),
                appointment.getEndTime()
        );
        if (!conflictingAppointments.isEmpty()) {
            // Ném ngoại lệ với thông báo chi tiết
            throw new RuntimeException("The selected staff is already booked during this time slot.");
        }
        // Lưu lịch hẹn nếu không có xung đột
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toAppointmentDTO(savedAppointment);
    }

    public List<AppointmentDetailDTO> getAllAppointmentsDetail() {
        return appointmentRepository.findAllAppointmentsWithDetails();
    }
    public AppointmentDetailDTO getAppointmentDetailById(int id) {
        return appointmentRepository.findAppointmentWithDetailsById(id);
    }

    private AppointmentDetailDTO convertToDetailDTO(Appointment appointment) {
        return new AppointmentDetailDTO(
                appointment.getId(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getMakeupDate(),
                appointment.getStatus(),
                appointment.getUser().getFullName(),
                appointment.getUser().getPhone(), // Thêm số điện thoại người dùng
                appointment.getServiceMakeup().getNameService(),
                appointment.getStaff().getNameStaff(),
                appointment.getUser().getId(),
                appointment.getServiceMakeup().getId(),
                appointment.getStaff().getId()
        );
    }

}