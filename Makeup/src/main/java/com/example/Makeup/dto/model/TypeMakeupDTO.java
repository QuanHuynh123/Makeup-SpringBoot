package com.example.Makeup.dto.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeMakeupDTO {
    int id;
    String nameMakeup;
    String description;
    double price;
    int timeMakeup;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
