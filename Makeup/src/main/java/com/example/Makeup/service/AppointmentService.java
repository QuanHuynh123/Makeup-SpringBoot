package com.example.Makeup.service;

import com.example.Makeup.dto.AppointmentDTO;
import com.example.Makeup.dto.WeekAppointmentsDTO;
import com.example.Makeup.entity.Appointment;
import com.example.Makeup.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<WeekAppointmentsDTO> getAppointmentsByMonth(int month, int year) {
        List<Appointment> appointments = appointmentRepository.findAppointmentsByMonth(month, year);
        List<AppointmentDTO> appointmentDTOs = appointments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return groupAppointmentsByWeek(appointmentDTOs, month, year);
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

    // Nhóm các lịch hẹn theo tuần trong tháng và thêm thông tin ngày bắt đầu, kết thúc của tuần
    private List<WeekAppointmentsDTO> groupAppointmentsByWeek(List<AppointmentDTO> appointments, int month, int year) {
        Map<Integer, List<AppointmentDTO>> weeklyAppointments = new HashMap<>();

        // Lấy các lịch hẹn và nhóm theo tuần trong tháng
        for (AppointmentDTO appointment : appointments) {
            int weekOfMonth = getWeekOfMonth(appointment.getMakeupDate());
            weeklyAppointments
                    .computeIfAbsent(weekOfMonth, k -> new ArrayList<>())
                    .add(appointment);
        }

        // Trả về danh sách các tuần, mỗi tuần có thêm thông tin ngày bắt đầu và kết thúc
        List<WeekAppointmentsDTO> weekAppointmentsDTOList = new ArrayList<>();
        for (Map.Entry<Integer, List<AppointmentDTO>> entry : weeklyAppointments.entrySet()) {
            int weekNumber = entry.getKey();
            List<AppointmentDTO> appointmentsInWeek = entry.getValue();

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
        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK); // Find the first day of the month
        calendar.add(Calendar.DAY_OF_MONTH, (weekNumber - 1) * 7 - firstDayOfMonth + 2); // Adjust to the start of the week
        return new Date(calendar.getTimeInMillis());
    }

    // Tính ngày kết thúc của tuần
    private Date getEndDateOfWeek(int weekNumber, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_MONTH, (weekNumber - 1) * 7 - firstDayOfMonth + 2 + 6); // Add 6 to get the end of the week
        return new Date(calendar.getTimeInMillis());
    }
}
