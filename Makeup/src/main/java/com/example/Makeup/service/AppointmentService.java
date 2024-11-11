package com.example.Makeup.service;

import com.example.Makeup.dto.AppointmentDTO;
import com.example.Makeup.entity.Appointment;
import com.example.Makeup.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<List<AppointmentDTO>> getAppointmentsByMonth(int month, int year) {
        List<Appointment> appointments = appointmentRepository.findAppointmentsByMonth(month, year);
        List<AppointmentDTO> appointmentDTOs = appointments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return groupAppointmentsByWeek(appointmentDTOs);
    }

    // Chuyển đổi từ entity Appointment sang AppointmentDTO
    private AppointmentDTO convertToDTO(Appointment appointment) {
        return new AppointmentDTO(
                appointment.getId(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getMakeupDate(),
                appointment.isStatus(),
                appointment.getUser().getId(),
                appointment.getServiceMakeup().getId(),
                appointment.getStaff().getId()
        );
    }

    // Nhóm các lịch hẹn theo tuần trong tháng
    private List<List<AppointmentDTO>> groupAppointmentsByWeek(List<AppointmentDTO> appointments) {
        Map<Integer, List<AppointmentDTO>> weeklyAppointments = new HashMap<>();

        // Lấy các lịch hẹn và nhóm theo tuần trong tháng
        for (AppointmentDTO appointment : appointments) {
            int weekOfMonth = getWeekOfMonth(appointment.getMakeupDate());
            weeklyAppointments
                    .computeIfAbsent(weekOfMonth, k -> new ArrayList<>())
                    .add(appointment);
        }

        // Trả về danh sách các tuần
        return new ArrayList<>(weeklyAppointments.values());
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
}
