package com.example.Makeup.entity;

import jakarta.persistence.*;
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

    @Column(name = "status", nullable = false)
    boolean status  ;

    @Column(name = "image" ,nullable = false)
    String image;

    @ManyToOne
    @JoinColumn(name = "subCategory_id")
    private SubCategory subCategory;
}
