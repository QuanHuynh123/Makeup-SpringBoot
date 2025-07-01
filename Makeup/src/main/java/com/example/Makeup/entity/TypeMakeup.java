package com.example.Makeup.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "type_makeup")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeMakeup extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @Column(name = "name_makeup", nullable = false)
    String nameMakeup;

    @Column(name = "description", length = 250)
    String description  ;

    @Column(name = "price" , nullable = false)
    double price  ;

    @Column(name = "time_makeup")
    int timeMakeup;

}
