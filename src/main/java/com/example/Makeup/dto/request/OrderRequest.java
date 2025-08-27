package com.example.Makeup.dto.request;

import jakarta.validation.constraints.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderRequest {

  @Email(message = "Invalid email format")
  private String email;

  @NotNull(message = "First name cannot be null")
  private String firstName;

  @NotNull(message = "Phone cannot be null")
  private String phoneNumber;

  private String message;

  String address;

  @NotNull(message = "Shipping type cannot be empty")
  @Positive(message = "Shipping type must be a positive number")
  @Min(1)
  @Max(2)
  int typeShipping;

  @NotNull(message = "Payment method cannot be empty")
  @Positive(message = "Payment method must be a positive number")
  @Min(1)
  @Max(2)
  private int paymentMethod;

  @NotNull(message = "Cannot be empty")
  @Positive(message = "Quantity must be a positive number")
  private int quantity;

  @NotNull(message = "Total price cannot be null")
  @Positive(message = "Total price must be a positive number")
  private double totalPrice;

  @NotEmpty(message = "Unique request ID cannot be empty")
  private String uniqueRequestId;

  @NotEmpty(message = "Order items cannot be empty")
  List<OrderItemRequest> orderItems;
}
