package com.example.Makeup.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    int id;
    int quantity;
    double price;
    Date useDate; // Ngày sử dụng dịch vụ
    int orderId; // ID của đơn hàng
    int productId;
}
