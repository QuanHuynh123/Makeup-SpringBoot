package com.example.Makeup.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDTO {
    int id;
    String userName;
    String passWord; // Có thể đổi tên thành password nếu cần thiết
    int userId;
    int roleId;
}
