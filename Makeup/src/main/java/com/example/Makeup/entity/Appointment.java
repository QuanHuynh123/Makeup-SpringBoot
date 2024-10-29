package com.example.Makeup.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.sql.Time;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "appointment")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @Column(name = "startTime", nullable = false)
    Time startTime  ;

    @Column(name = "endTime", nullable = false)
    Time endTime  ;

    @Column(name = "makeupDate",  nullable = false)
    Date makeupDate;

    @Column(name = "status",  nullable = false)
    boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serviceMakeup_id", nullable = false)
    ServiceMakeup serviceMakeup; // Kết nối đến dịch vụ makeup

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = false)
    Staff staff; // Liên kết với nhân viên
}
