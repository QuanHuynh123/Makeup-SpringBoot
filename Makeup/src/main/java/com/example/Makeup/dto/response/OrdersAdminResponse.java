package com.example.Makeup.dto.response;

import com.example.Makeup.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
public class OrdersAdminResponse {

  final UUID id;
  double totalPrice;
  int totalQuantity;

  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDateTime orderDate;

  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDateTime pickupDate;

  @JsonFormat(pattern = "yyyy-MM-dd")
  LocalDateTime returnDate;

  OrderStatus status;
  UUID userId;
  String nameUser;
  String phone;
  int paymentId;
  String paymentMethod;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
}
