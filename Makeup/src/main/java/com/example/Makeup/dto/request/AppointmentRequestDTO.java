package com.example.Makeup.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Time;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentRequestDTO {

    Time startTime;
    Time endTime;
    LocalDate makeupDate;
    Integer serviceMakeupId;
    UUID staffId;
    GuestInfo guestInfo;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class GuestInfo {
        String fullName;
        String email;
        String phone;
        String message;
    }
}
