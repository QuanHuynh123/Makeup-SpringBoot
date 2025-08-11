package com.example.Makeup.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayRequest {

  @Positive(message = "Price must be a positive number")
  private int amount;

  @NotEmpty(message = "Order information is required")
  private String orderInfo;

  @NotNull(message = "Order ID cannot be null")
  private UUID orderId;
}
