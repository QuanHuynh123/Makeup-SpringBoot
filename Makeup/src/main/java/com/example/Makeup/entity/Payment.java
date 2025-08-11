package com.example.Makeup.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "payment")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment extends Base {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  int id;

  @Column(name = "type_payment", length = 250, nullable = false)
  String namePaymentMethod;

  @Column(name = "status", nullable = false)
  boolean status;
}
