package com.example.Makeup.dto.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
  final UUID id;
  String fullName;
  String email;
  String address;
  String phone;
  Integer role;
  UUID cartId;
  UUID accountId;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
}
