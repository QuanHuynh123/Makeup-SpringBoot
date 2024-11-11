package com.example.Makeup.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Time;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "serviceMakeup")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceMakeup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @Column(name = "nameservice", nullable = false)
    String nameService  ;

    @Column(name = "description", length = 250)
    String description  ;

    @Column(name = "price" , nullable = false)
    double price  ;

    @Column(name = "time", nullable = false)
    int time  ;


    public ServiceMakeup(String nameService, String description, double price, int time) {
        this.nameService = nameService;
        this.description = description;
        this.price = price;
        this.time=time;
    }
}
