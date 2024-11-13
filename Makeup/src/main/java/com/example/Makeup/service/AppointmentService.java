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

        // Nhóm các lịch hẹn theo tuần trong tháng
        for (AppointmentDTO appointment : appointments) {
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
            List<AppointmentDTO> appointmentsInWeek = weeklyAppointments.getOrDefault(weekNumber, new ArrayList<>());

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
}