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
@Table(name = "user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @Column(name = "fullname", length = 40, nullable = false)
    String fullName;

    @Column(name = "email", length = 40, nullable = false)
    String email;

    @Column(name = "address", length = 50, nullable = false)
    String address;

    @Column(name = "phone", length = 20, nullable = false)
    String phone;

    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Order> orders;

    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY ,  cascade = CascadeType.ALL)
    List<Appointment> appointments;

    @OneToOne(mappedBy ="user" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", nullable = false)
    Cart cart;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    Account account;
}
