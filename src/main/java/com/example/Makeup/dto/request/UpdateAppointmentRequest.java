package com.example.Makeup.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import java.sql.Time;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAppointmentRequest {

  @NotNull(message = "Start time is required")
  @JsonFormat(pattern = "HH:mm:ss")
  Time startTime;

  @NotNull(message = "End time is required")
  LocalDate makeupDate;

  boolean status;

  @Positive(message = "Type Makeup ID must be a positive number")
  @Min(1)
  @Max(2)
  int typeMakeupId;

  @NotNull(message = "Staff ID cannot be null")
  UUID staffId;
}
