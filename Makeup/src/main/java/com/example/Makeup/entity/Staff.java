package com.example.Makeup.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "staff")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Staff extends Base {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
  @JdbcTypeCode(SqlTypes.CHAR)
  private UUID id;

  @Column(name = "name", length = 40)
  String nameStaff;

  @Column(name = "phone", length = 20)
  String phone;

  @OneToMany(mappedBy = "staff", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  List<Appointment> appointments;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  Account account;
}
