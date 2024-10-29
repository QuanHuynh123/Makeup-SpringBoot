package com.example.Makeup.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "feedBack")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @Column(name = "rating", nullable = false)
    int rating  ;

    @Column(name = "comment", length = 250, nullable = false)
    String comment;

    @Column(name = "reviewDate",  nullable = false)
    Date reviewDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "serviceMakeup_id", nullable = false)
    ServiceMakeup serviceMakeup;
}
