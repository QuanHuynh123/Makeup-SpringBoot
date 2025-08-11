package com.example.Makeup.entity;

import jakarta.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "appointment")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Appointment extends Base {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
  @JdbcTypeCode(SqlTypes.CHAR)
  private UUID id;

  @Column(name = "start_time", nullable = false)
  Time startTime;

  @Column(name = "end_time", nullable = false)
  Time endTime;

  @Column(name = "makeup_date", nullable = false)
  LocalDate makeupDate;

  @Column(name = "price", nullable = false)
  Double price;

  @Column(name = "status", nullable = false)
  boolean status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "type_makeup_id", nullable = false)
  TypeMakeup typeMakeup;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "staff_id")
  Staff staff;
}
