package com.example.Makeup.controller.web.admin;

import com.example.Makeup.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class AdminController {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/test")
    public String loginPage(){
        return "admin";
    }

    @GetMapping("/test2")
    public String loginPage2(){
        return "schedule";
    }

    @GetMapping("/appointments/stats/{year}")
    public ResponseEntity<Map<Integer, Long>> getAppointmentStats(@PathVariable int year) {
        try {
            List<Object[]> results = appointmentRepository.findAppointmentsCountByMonth(year);

            Map<Integer, Long> stats = new HashMap<>();

            for (Object[] row : results) {
                Integer month = (Integer) row[0];
                Long count = ((Number) row[1]).longValue();
                stats.put(month, count); // Chỉ cập nhật các tháng có dữ liệu
            }

            // Log để kiểm tra dữ liệu trả về
            System.out.println("Appointment Stats: " + stats);

            return ResponseEntity.ok(stats); // Trả về mã thành công 200
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Trả về mã lỗi 400
        }
    }


    @GetMapping("/appointments/stats/last30Days")
    public ResponseEntity<Map<String, Long>> getLast30DaysStats() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -30);  // Lùi 30 ngày
        Date startDate = calendar.getTime();
        Date endDate = new Date();

        List<Object[]> results = appointmentRepository.findAppointmentsCountByDateRange(startDate, endDate);

        Map<String, Long> stats = new HashMap<>();
        for (Object[] row : results) {
            String date = (String) row[0]; // Ngày
            Long count = ((Number) row[1]).longValue();
            stats.put(date, count); // Chỉ cập nhật các ngày có dữ liệu
        }

        return ResponseEntity.ok(stats);
    }


    @GetMapping("/appointments/stats/last7Days")
    public ResponseEntity<Map<String, Long>> getLast7DaysStats() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);  // Lùi 7 ngày
        Date startDate = calendar.getTime();
        Date endDate = new Date();

        List<Object[]> results = appointmentRepository.findAppointmentsCountByDateRange(startDate, endDate);

        Map<String, Long> stats = new HashMap<>();
        for (Object[] row : results) {
            String date = (String) row[0]; // Ngày
            Long count = ((Number) row[1]).longValue();
            stats.put(date, count); // Chỉ cập nhật các ngày có dữ liệu
        }

        return ResponseEntity.ok(stats);
    }


    @GetMapping("/appointments/stats/thisMonth")
    public ResponseEntity<Map<Integer, Long>> getThisMonthStats() {
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1; // Lấy tháng hiện tại
        int currentYear = calendar.get(Calendar.YEAR); // Lấy năm hiện tại

        List<Object[]> results = appointmentRepository.findAppointmentsCountByCurrentMonth(currentMonth, currentYear);

        Map<Integer, Long> stats = new HashMap<>();
        for (Object[] row : results) {
            Integer day = (Integer) row[0];
            Long count = ((Number) row[1]).longValue();
            stats.put(day, count); // Chỉ cập nhật các ngày có dữ liệu
        }

        return ResponseEntity.ok(stats);
    }



}
