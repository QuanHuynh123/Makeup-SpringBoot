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
@Table(name = "cart")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @Column(name = "totalPrice", nullable = false)
    double totalPrice  ;

    @Column(name = "totalQuantity", nullable = false)
    int totalQuantity  ;

    @Column(name = "createDate",  nullable = false)
    Date createDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    User user;
}
