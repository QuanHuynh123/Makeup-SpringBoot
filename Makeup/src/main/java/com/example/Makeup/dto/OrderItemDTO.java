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
    Date useDate;
    int orderId;
    int productId;
    String productName;
}
