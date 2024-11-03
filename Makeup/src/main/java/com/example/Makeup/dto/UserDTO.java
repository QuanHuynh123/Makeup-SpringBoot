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
    int id; // ID của người dùng
    String fullName; // Tên đầy đủ
    String email; // Địa chỉ email
    String address; // Địa chỉ
    String phone; // Số điện thoại
    //List<OrderDTO> orders; // Danh sách đơn hàng
    //List<AppointmentDTO> appointments; // Danh sách cuộc hẹn
    int cartId;
}
