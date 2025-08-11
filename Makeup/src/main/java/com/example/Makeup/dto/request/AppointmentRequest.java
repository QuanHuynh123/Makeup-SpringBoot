package com.example.Makeup.dto.request;

import java.sql.Time;
import java.time.LocalDate;
import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentRequest {

  Time startTime;
  Time endTime;
  LocalDate makeupDate;
  Integer typeMakeupId;
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
