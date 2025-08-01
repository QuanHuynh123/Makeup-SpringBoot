package com.example.Makeup.dto.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentDTO {
    UUID id;
    Time startTime;
    Time endTime;
    Double price;
    LocalDate makeupDate;
    boolean status;
    UUID userId;
    int typeMakeupId;
    UUID staffId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}