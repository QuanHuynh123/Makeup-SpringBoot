package com.example.Makeup.dto.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
public class FeedBackDTO {
    final UUID id;
    int rating;
    String comment;
    UUID userId;
    int serviceMakeupId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
