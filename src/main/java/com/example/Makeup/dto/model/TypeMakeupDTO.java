package com.example.Makeup.dto.model;

import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
