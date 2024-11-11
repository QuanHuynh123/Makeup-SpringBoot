package com.example.Makeup.repository;

import com.example.Makeup.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByMakeupDateBetween(Date startDate, Date endDate);
    @Query("SELECT a FROM Appointment a WHERE MONTH(a.makeupDate) = :month AND YEAR(a.makeupDate) = :year")
    List<Appointment> findAppointmentsByMonth(@Param("month") int month, @Param("year") int year);

}
