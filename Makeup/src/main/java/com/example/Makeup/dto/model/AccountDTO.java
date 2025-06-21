package com.example.Makeup.dto.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDTO {
    UUID id;
    String userName;
    String passWord;
    int roleId;
    UUID staffId;
    UUID userId;
    LocalDateTime createDate;
    LocalDateTime updateDate;
}
