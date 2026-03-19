package com.example.Makeup.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartItemRequest {

  @NotNull(message = "Cart item ID is required")
  UUID cartItemId;

  @NotNull(message = "Product ID is required")
  UUID productId;

  @NotNull(message = "Quantity is required")
  @Min(value = 1, message = "Quantity must be at least 1")
  @Max(value = 100, message = "Quantity must not exceed 100")
  Integer quantity;

  @NotNull(message = "Rental date is required")
  LocalDateTime rentalDate;
}
