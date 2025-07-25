package com.example.Makeup.dto.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffDTO {
    UUID id;
    String nameStaff;
    String phone;
    String accountId;
    List<AppointmentDTO> appointments;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}