package com.example.Makeup.dto.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Time;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceMakeupDTO {
    int id;
    String nameService;
    String description;
    double price;
    int timeService;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
