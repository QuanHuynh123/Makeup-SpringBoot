package com.example.Makeup.repository;

import com.example.Makeup.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByMakeupDateBetween(Date startDate, Date endDate);
}
