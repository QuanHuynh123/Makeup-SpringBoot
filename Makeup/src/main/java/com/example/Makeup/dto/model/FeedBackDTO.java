package com.example.Makeup.dto.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
public class FeedBackDTO {
  final UUID id;
  int rating;
  String comment;
  UUID userId;
  int typeMakeupId;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
}
