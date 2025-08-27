package com.example.Makeup.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateFeedBackRequest {

  @NotNull(message = "Rating id cannot be null")
  UUID id;

  @NotNull(message = "Rating cannot be null")
  @Positive(message = "Rating must be a positive number")
  int rating;

  @NotNull(message = "Comment cannot be null")
  String comment;

  @NotNull(message = "Type makeup ID cannot be null")
  @Positive(message = "Type makeup ID must be a positive number")
  int typeMakeupId;
}
