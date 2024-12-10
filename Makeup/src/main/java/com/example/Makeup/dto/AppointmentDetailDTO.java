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
public class AppointmentDetailDTO {
    int id;
    Time startTime;
    Time endTime;
    Date makeupDate;
    boolean status;
    String userName;
    String userPhone;
    String serviceMakeupName;
    String staffName;
    int userID;
    int serviceMakeupID;
    int staffID;

    @Override
    public String toString() {
        return "AppointmentDetailDTO{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", makeupDate=" + makeupDate +
                ", status=" + status +
                ", userName='" + userName + '\'' +
                ", serviceMakeupName='" + serviceMakeupName + '\'' +
                ", staffName='" + staffName + '\'' +
                ", userID=" + userID +
                ", serviceMakeupID=" + serviceMakeupID +
                ", staffID=" + staffID +
                '}';
    }

}