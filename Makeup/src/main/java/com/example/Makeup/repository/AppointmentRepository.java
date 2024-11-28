package com.example.Makeup.repository;

import com.example.Makeup.entity.Appointment;
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

    @Query("SELECT a FROM Appointment a WHERE MONTH(a.makeupDate) = :month AND YEAR(a.makeupDate) = :year")
    List<Appointment> findAppointmentsByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT a FROM Appointment a WHERE a.staff.id = :staffId " +
            "AND a.makeupDate = :makeupDate " +
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
}
