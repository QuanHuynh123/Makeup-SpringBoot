package com.example.Makeup.repository;

import com.example.Makeup.dto.AppointmentDetailDTO;
import com.example.Makeup.entity.Appointment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByMakeupDateBetween(Date startDate, Date endDate);
    List<Appointment> findByStaffId(int staffId);

    List<Appointment> findAllByUserId(int userId);

    @Query("SELECT new com.example.Makeup.dto.AppointmentDetailDTO(" +
            "a.id, a.startTime, a.endTime, a.makeupDate, a.status, " +
            "u.fullName, u.phone, s.nameService, st.nameStaff, " +
            "u.id, s.id, st.id) " +
            "FROM Appointment a " +
            "JOIN a.user u " +
            "JOIN a.serviceMakeup s " +
            "JOIN a.staff st " +
            "WHERE MONTH(a.makeupDate) = :month AND YEAR(a.makeupDate) = :year")
    List<AppointmentDetailDTO> findAppointmentsByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT new com.example.Makeup.dto.AppointmentDetailDTO(" +
            "a.id, a.startTime, a.endTime, a.makeupDate, a.status, " +
            "u.fullName, u.phone, s.nameService, st.nameStaff, " +
            "u.id, s.id, st.id) " +
            "FROM Appointment a " +
            "JOIN a.user u " +
            "JOIN a.serviceMakeup s " +
            "JOIN a.staff st " +
            "WHERE MONTH(a.makeupDate) = :month AND YEAR(a.makeupDate) = :year AND st.id = :staffID")
    List<AppointmentDetailDTO> findAppointmentsByMonthAndStaff(@Param("month") int month, @Param("year") int year, @Param("staffID") int staffID);


    @Query("SELECT a FROM Appointment a WHERE a.staff.id = :staffId " +
            "AND a.makeupDate = :makeupDate " +
            "AND a.status = true " +  // Thêm điều kiện status = 1
            "AND ((:startTime BETWEEN a.startTime AND a.endTime) " +
            "OR (:endTime BETWEEN a.startTime AND a.endTime) " +
            "OR (a.startTime BETWEEN :startTime AND :endTime) " +
            "OR (a.endTime BETWEEN :startTime AND :endTime))")
    List<Appointment> findConflictingAppointments(
            @Param("staffId") int staffId,
            @Param("makeupDate") Date makeupDate,
            @Param("startTime") Time startTime,
            @Param("endTime") Time endTime
    );


    // Truy vấn số lượng appointment theo từng tháng trong năm
    @Query("SELECT MONTH(a.makeupDate) AS month, COUNT(a) AS count " +
            "FROM Appointment a " +
            "WHERE YEAR(a.makeupDate) = :year " +
            "GROUP BY MONTH(a.makeupDate) " +
            "ORDER BY month")
    List<Object[]> findAppointmentsCountByMonth(int year);

    @Query("SELECT DATE_FORMAT(a.makeupDate, '%Y-%m-%d'), COUNT(a) " +
            "FROM Appointment a " +
            "WHERE a.makeupDate BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE(a.makeupDate) " +
            "ORDER BY DATE(a.makeupDate) ASC")
    List<Object[]> findAppointmentsCountByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT DAY(a.makeupDate), COUNT(a) " +
            "FROM Appointment a " +
            "WHERE MONTH(a.makeupDate) = :currentMonth AND YEAR(a.makeupDate) = :currentYear " +
            "GROUP BY DAY(a.makeupDate) " +
            "ORDER BY DAY(a.makeupDate) ASC")
    List<Object[]> findAppointmentsCountByCurrentMonth(@Param("currentMonth") int currentMonth, @Param("currentYear") int currentYear);

    @Query("SELECT new com.example.Makeup.dto.AppointmentDetailDTO(" +
            "a.id, a.startTime, a.endTime, a.makeupDate, a.status, " +
            "u.fullName, u.phone, s.nameService, st.nameStaff, " +
            "u.id, s.id, st.id) " +
            "FROM Appointment a " +
            "JOIN a.user u " +
            "JOIN a.serviceMakeup s " +
            "JOIN a.staff st")
    List<AppointmentDetailDTO> findAllAppointmentsWithDetails();

    @Query("SELECT new com.example.Makeup.dto.AppointmentDetailDTO(" +
            "a.id, a.startTime, a.endTime, a.makeupDate, a.status, " +
            "u.fullName, u.phone, s.nameService, st.nameStaff, " +
            "u.id, s.id, st.id) " +
            "FROM Appointment a " +
            "JOIN a.user u " +
            "JOIN a.serviceMakeup s " +
            "JOIN a.staff st " +
            "WHERE a.id = :id")
    AppointmentDetailDTO findAppointmentWithDetailsById(@Param("id") int id);


    @Query("SELECT a FROM Appointment a WHERE a.staff.id = :staffId AND a.makeupDate = :makeupDate")
    List<Appointment> findAppointmentsByDateAndStaff(
            @Param("staffId") int staffId,
            @Param("makeupDate") Date makeupDate
    );


}