package com.example.Makeup.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "service_makeup")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceMakeup extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @Column(name = "name_service", nullable = false)
    String nameService  ;

    @Column(name = "description", length = 250)
    String description  ;

    @Column(name = "price" , nullable = false)
    double price  ;

    @Column(name = "time_service")
    int timeService  ;

}
