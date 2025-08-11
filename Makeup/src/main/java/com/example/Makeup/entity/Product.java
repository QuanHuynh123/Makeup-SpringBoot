package com.example.Makeup.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "product")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends Base {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
  @JdbcTypeCode(SqlTypes.CHAR)
  private UUID id;

  @Column(name = "name_product", nullable = false)
  String nameProduct;

  @Column(name = "description", length = 250)
  String description;

  @Column(name = "size", length = 10, nullable = false)
  String size;

  @Column(name = "price", nullable = false)
  double price;

  @Column(name = "status")
  boolean status;

  @Column(name = "image")
  String image;

  @Column(name = "current_quantity", nullable = false)
  int currentQuantity; // This is the current available quantity of the product

  @Column(name = "quantity", nullable = false)
  int quantity;

  @Version
  @Column(name = "version")
  private int version;

  @Column(name = "rental_count")
  int rentalCount;

  @ManyToOne
  @JoinColumn(name = "sub_category_id")
  private SubCategory subCategory;
}
