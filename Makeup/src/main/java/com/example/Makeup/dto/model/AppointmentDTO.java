package com.example.Makeup.dto.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentDTO {
    final UUID id;
    Time startTime;
    Time endTime;
    LocalDateTime makeupDate;
    boolean status;
    UUID userId;
    int serviceMakeupId;
    UUID staffId;
    LocalDateTime createDate;
    LocalDateTime updateDate;
}