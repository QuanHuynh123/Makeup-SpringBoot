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

    public UserDTO(String fullName , String email , String address , String phone){
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    //List<OrderDTO> orders; // Danh sách đơn hàng
    //List<AppointmentDTO> appointments; // Danh sách cuộc hẹn
}
