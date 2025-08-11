package com.example.Makeup.repository;

import com.example.Makeup.dto.model.AppointmentDTO;
import com.example.Makeup.dto.response.AppointmentsAdminResponse;
import com.example.Makeup.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    List<Appointment> findByStaffId(UUID staffId);

    @Query("SELECT new com.example.Makeup.dto.response.AppointmentsAdminResponse(" +
            "a.id, a.startTime, a.endTime, a.makeupDate, a.status, s.price, " +
            "u.id, s.id, st.id, st.nameStaff, s.nameMakeup, u.fullName , u.phone,  a.createdAt, a.updatedAt) " +
            "FROM Appointment a " +
            "JOIN a.user u " +
            "JOIN a.typeMakeup s " +
            "JOIN a.staff st " +
            "WHERE a.user.id = :userId")
    List<AppointmentsAdminResponse> findAllByUserId(UUID userId);

    @Query("SELECT new com.example.Makeup.dto.response.AppointmentsAdminResponse(" +
            "a.id, a.startTime, a.endTime, a.makeupDate, a.status, s.price, " +
            "u.id, s.id, st.id, st.nameStaff, s.nameMakeup, u.fullName ,  u.phone,  a.createdAt, a.updatedAt) " +
            "FROM Appointment a " +
            "JOIN a.user u " +
            "JOIN a.typeMakeup s " +
            "JOIN a.staff st " )
    List<AppointmentsAdminResponse> findAllAppointments();

    @Query("SELECT new com.example.Makeup.dto.response.AppointmentsAdminResponse(" +
            "a.id, a.startTime, a.endTime, a.makeupDate, a.status, s.price, " +
            "u.id, s.id, st.id, st.nameStaff, s.nameMakeup, u.fullName , u.phone,  a.createdAt, a.updatedAt) " +
            "FROM Appointment a " +
            "JOIN a.user u " +
            "JOIN a.typeMakeup s " +
            "JOIN a.staff st " +
            "WHERE MONTH(a.makeupDate) = :month AND YEAR(a.makeupDate) = :year " +
            "AND (:staffID IS NULL OR st.id = :staffID)")
    List<AppointmentsAdminResponse> findAppointmentsByMonth(@Param("month") int month,
                                                 @Param("year") int year,
                                                 @Param("staffID") UUID staffID);

    @Query("""
    SELECT a FROM Appointment a
    WHERE a.staff.id = :staffId
    AND a.makeupDate = :makeupDate
    AND a.status = true
    AND (:startTime < a.endTime AND :endTime > a.startTime)
    """)
    List<Appointment> findConflictingAppointments(
            @Param("staffId") UUID staffId,
            @Param("makeupDate") LocalDate makeupDate,
            @Param("startTime")  Time startTime,
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

    @Query("SELECT new com.example.Makeup.dto.model.AppointmentDTO(" +
            "a.id, a.startTime, a.endTime, a.makeupDate, a.status, " +
            "u.fullName, u.phone, s.nameMakeup, st.nameStaff, " +
            "u.id, s.id, st.id) " +
            "FROM Appointment a " +
            "JOIN a.user u " +
            "JOIN a.typeMakeup s " +
            "JOIN a.staff st")
    List<AppointmentDTO> findAllAppointmentsWithDetails();

    @Query("SELECT new com.example.Makeup.dto.model.AppointmentDTO(" +
            "a.id, a.startTime, a.endTime, a.makeupDate, a.status, " +
            "u.fullName, u.phone, s.nameMakeup, st.nameStaff, " +
            "u.id, s.id, st.id) " +
            "FROM Appointment a " +
            "JOIN a.user u " +
            "JOIN a.typeMakeup s " +
            "JOIN a.staff st " +
            "WHERE a.id = :id")
    AppointmentDTO findAppointmentWithDetailsById(@Param("id") int id);


    @Query("SELECT a FROM Appointment a WHERE a.staff.id = :staffId AND a.status = true " +
            "AND a.makeupDate = :makeupDate")
    List<Appointment> findAppointmentsByDateAndStaff(
            @Param("staffId") UUID staffId,
            @Param("makeupDate") LocalDate makeupDate);

}