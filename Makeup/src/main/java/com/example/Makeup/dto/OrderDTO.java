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
    boolean status;
    int userId; // ID của người dùng
    int paymentId; // ID của phương thức thanh toán
}
