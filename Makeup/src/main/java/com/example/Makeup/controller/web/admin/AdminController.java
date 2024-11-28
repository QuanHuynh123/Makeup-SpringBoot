package com.example.Makeup.controller.web.admin;

import com.example.Makeup.dto.StaffDTO;
import com.example.Makeup.repository.AppointmentRepository;
import com.example.Makeup.repository.OrderRepository;
import com.example.Makeup.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AdminController {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private StaffService staffService;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/test")
    public String loginPage(Model model) { // Thêm tham số Model
        List<StaffDTO> staffList = staffService.getAllStaff();
        model.addAttribute("staffList", staffList); // Truyền danh sách nhân viên vào model
        return "customer-all"; // Tên của file Thymeleaf (customer-all.html)
    }


    @GetMapping("/test2")
    public String loginPage2(){
        return "admin";
    }

    @GetMapping("/test3")
    public String loginPage3(){
        return "dashboard";
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

        // Map dữ liệu theo thứ tự query trả về
        LinkedHashMap<String, Long> stats = new LinkedHashMap<>();
        for (Object[] row : results) {
            String date = (String) row[0]; // Ngày
            Long count = ((Number) row[1]).longValue();
            stats.put(date, count);
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

        // Map dữ liệu theo thứ tự query trả về
        LinkedHashMap<String, Long> stats = new LinkedHashMap<>();
        for (Object[] row : results) {
            String date = (String) row[0]; // Ngày
            Long count = ((Number) row[1]).longValue();
            stats.put(date, count);
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

    @GetMapping("/appointments/stats/customRange")
    public ResponseEntity<Map<String, Long>> getCustomRangeStats(@RequestParam("startDate") String startDateStr, @RequestParam("endDate") String endDateStr) {
        try {
            // Chuyển đổi chuỗi ngày thành đối tượng Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);

            // Lấy kết quả từ repository với phạm vi ngày đã cho
            List<Object[]> results = appointmentRepository.findAppointmentsCountByDateRange(startDate, endDate);

            // Map dữ liệu theo thứ tự query trả về
            LinkedHashMap<String, Long> stats = new LinkedHashMap<>();
            for (Object[] row : results) {
                String date = (String) row[0]; // Ngày
                Long count = ((Number) row[1]).longValue();
                stats.put(date, count);
            }

            return ResponseEntity.ok(stats); // Trả về thống kê
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Trả về mã lỗi nếu có lỗi
        }
    }


















    @GetMapping("/orders/stats/{year}")
    public ResponseEntity<Map<Integer, Long>> getOrderStats(@PathVariable int year) {
        try {
            List<Object[]> results = orderRepository.findOrdersCountByMonth(year);

            Map<Integer, Long> stats = new HashMap<>();

            for (Object[] row : results) {
                Integer month = (Integer) row[0];
                Long count = ((Number) row[1]).longValue();
                stats.put(month, count); // Chỉ cập nhật các tháng có dữ liệu
            }

            // Log để kiểm tra dữ liệu trả về
            System.out.println("Order Stats: " + stats);

            return ResponseEntity.ok(stats); // Trả về mã thành công 200
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Trả về mã lỗi 400
        }
    }

    @GetMapping("/orders/stats/last30Days")
    public ResponseEntity<Map<String, Long>> getLast30DaysOrderStats() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -30); // Lùi 30 ngày
        Date startDateUtil = calendar.getTime();
        Date endDateUtil = new Date();

        // Chuyển đổi sang java.sql.Date
        java.sql.Date startDate = new java.sql.Date(startDateUtil.getTime());
        java.sql.Date endDate = new java.sql.Date(endDateUtil.getTime());

        List<Object[]> results = orderRepository.findOrdersCountByDateRange(startDate, endDate);

        LinkedHashMap<String, Long> stats = new LinkedHashMap<>();
        for (Object[] row : results) {
            String date = (String) row[0]; // Ngày
            Long count = ((Number) row[1]).longValue();
            stats.put(date, count);
        }

        return ResponseEntity.ok(stats);
    }


    @GetMapping("/orders/stats/last7Days")
    public ResponseEntity<Map<String, Long>> getLast7DaysOrderStats() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7); // Lùi 7 ngày
        Date startDateUtil = calendar.getTime();
        Date endDateUtil = new Date();

        // Chuyển đổi sang java.sql.Date
        java.sql.Date startDate = new java.sql.Date(startDateUtil.getTime());
        java.sql.Date endDate = new java.sql.Date(endDateUtil.getTime());

        List<Object[]> results = orderRepository.findOrdersCountByDateRange(startDate, endDate);

        LinkedHashMap<String, Long> stats = new LinkedHashMap<>();
        for (Object[] row : results) {
            String date = (String) row[0]; // Ngày
            Long count = ((Number) row[1]).longValue();
            stats.put(date, count);
        }

        return ResponseEntity.ok(stats);
    }


    @GetMapping("/orders/stats/thisMonth")
    public ResponseEntity<Map<Integer, Long>> getThisMonthOrderStats() {
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1; // Lấy tháng hiện tại
        int currentYear = calendar.get(Calendar.YEAR); // Lấy năm hiện tại

        List<Object[]> results = orderRepository.findOrdersCountByCurrentMonth(currentMonth, currentYear);

        Map<Integer, Long> stats = new HashMap<>();
        for (Object[] row : results) {
            Integer day = (Integer) row[0];
            Long count = ((Number) row[1]).longValue();
            stats.put(day, count); // Chỉ cập nhật các ngày có dữ liệu
        }

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/orders/stats/customRange")
    public ResponseEntity<Map<String, Long>> getCustomRangeOrderStats(
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr) {
        try {
            // Chuyển đổi chuỗi ngày thành đối tượng java.util.Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDateUtil = dateFormat.parse(startDateStr);
            Date endDateUtil = dateFormat.parse(endDateStr);

            // Chuyển đổi sang java.sql.Date
            java.sql.Date startDate = new java.sql.Date(startDateUtil.getTime());
            java.sql.Date endDate = new java.sql.Date(endDateUtil.getTime());

            // Lấy kết quả từ repository với phạm vi ngày đã cho
            List<Object[]> results = orderRepository.findOrdersCountByDateRange(startDate, endDate);

            // Map dữ liệu theo thứ tự query trả về
            LinkedHashMap<String, Long> stats = new LinkedHashMap<>();
            for (Object[] row : results) {
                String date = (String) row[0]; // Ngày
                Long count = ((Number) row[1]).longValue();
                stats.put(date, count);
            }

            return ResponseEntity.ok(stats); // Trả về thống kê
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Trả về mã lỗi nếu có lỗi
        }
    }




}
