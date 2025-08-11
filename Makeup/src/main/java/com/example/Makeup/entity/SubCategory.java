package com.example.Makeup.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "sub_category")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubCategory extends Base {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  int id;

  @Column(name = "name", length = 100, nullable = false)
  String name;

  @Column(name = "status", nullable = false)
  Boolean status;

  @OneToMany(mappedBy = "subCategory", fetch = FetchType.LAZY)
  private List<Product> product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false) // Sửa cột tham chiếu
  private Category category;
}
