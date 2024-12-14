package com.example.Makeup.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    int id;
    double totalPrice;
    int totalQuantity;
    Date orderDate;
    Date pickupDate;
    String phone;
    String name ;
    boolean status;
    int userId;
    int paymentId;
}
