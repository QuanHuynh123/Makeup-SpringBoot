package com.example.Makeup.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "subCategory")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @Column(name = "name", length = 100, nullable = false)
    String name;

    @OneToMany(mappedBy = "subCategory",  fetch = FetchType.LAZY)
    private List<Product> product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subCategory_id",nullable = false)
    Category category;

    public SubCategory(String name, int idCategory) {
        this.name = name;
        this.category = new Category(idCategory); // Tạo Category từ ID
    }
}
