package com.example.Makeup.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    int id;
    String fullName;
    String email;
    String address;
    String phone;
    int role ;
    int cartId;
    int accountId;

    //List<OrderDTO> orders; // Danh sách đơn hàng
    //List<AppointmentDTO> appointments; // Danh sách cuộc hẹn
}
