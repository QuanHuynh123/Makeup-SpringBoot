package com.example.Makeup.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "product")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @Column(name = "nameproduct", nullable = false)
    String nameProduct ;

    @Column(name = "description", length = 250)
    String description  ;

    @Column(name = "size", length = 10, nullable = false)
    String size  ;

    @Column(name = "price", nullable = false)
    double price  ;

    @Column(name = "status")
    boolean status  ;

    @Column(name = "image")
    String image;

    @Column(name = "rental_count")
    int rentalCount; // Số lượt quần áo đã thuê

    @Column(name = "created_at", updatable = false) //updatable = cấm update, final
    Date createdAt; // Ngày tạo sản phẩm
    
    @ManyToOne
    @JoinColumn(name = "subCategory_id")
    private SubCategory subCategory;
}
