package com.example.Makeup.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceMakeupDTO {
    int id; // ID của dịch vụ makeup
    String nameService; // Tên dịch vụ
    String description; // Mô tả dịch vụ
    double price; // Giá dịch vụ
}
