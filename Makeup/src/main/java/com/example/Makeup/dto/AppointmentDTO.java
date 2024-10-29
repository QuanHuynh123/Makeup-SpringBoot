package com.example.Makeup.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentDTO {
    int id;
    Time startTime;
    Time endTime;
    Date makeupDate;
    boolean status;
    int userId; // ID người dùng
    int serviceMakeupId; // ID dịch vụ makeup
    int staffId; // ID nhân viên
}
