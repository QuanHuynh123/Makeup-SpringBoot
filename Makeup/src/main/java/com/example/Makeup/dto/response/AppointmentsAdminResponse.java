package com.example.Makeup.dto.response;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentsAdminResponse {
  UUID id;
  Time startTime;
  Time endTime;
  LocalDate makeupDate;
  boolean status;
  Double price;
  UUID userId;
  int typeMakeupId;
  UUID staffId;
  String nameStaff;
  String typeMakeupName;
  String nameUser;
  String phoneUser;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
}
