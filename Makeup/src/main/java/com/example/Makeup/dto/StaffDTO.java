package com.example.Makeup.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffDTO {
    int id;
    String nameStaff;
    String phone;
    String accountId;
}