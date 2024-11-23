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
}
