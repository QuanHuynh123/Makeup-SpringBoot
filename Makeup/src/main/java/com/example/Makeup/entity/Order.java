package com.example.Makeup.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @Column(name = "totalPrice", nullable = false)
    double totalPrice  ;

    @Column(name = "totalQuantity", nullable = false)
    int totalQuantity  ;

    @Column(name = "orderDate",  nullable = false)
    Date orderDate;

    @Column(name = "status",  nullable = false)
    boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "pickupDate") // Cột ngày lấy
    Date pickupDate; // Ngày lấy do nhân viên chỉ định

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    Payment payment;

    public Order(double totalPrice, int totalQuantity, Date orderDate, boolean status, User user, Payment payment) {
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.orderDate = orderDate;
        this.status = status;
        this.user = user;
        this.payment = payment;
    }
}
