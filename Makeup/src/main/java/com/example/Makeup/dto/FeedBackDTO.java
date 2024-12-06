package com.example.Makeup.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedBackDTO {
    int id;
    int rating;
    String comment;
    Date reviewDate;
    int userId; // ID của người dùng
    String nameUser;
    int serviceMakeupId; // ID của dịch vụ makeup
}
