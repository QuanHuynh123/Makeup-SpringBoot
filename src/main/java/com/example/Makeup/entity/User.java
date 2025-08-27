package com.example.Makeup.entity;

import jakarta.persistence.*;
import java.util.List;
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
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends Base {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
  @JdbcTypeCode(SqlTypes.CHAR)
  private UUID id;

  @Column(name = "full_name", length = 40)
  String fullName;

  @Column(name = "email", length = 40)
  String email;

  @Column(name = "address", length = 50)
  String address;

  @Column(name = "phone", length = 20, nullable = false)
  String phone;

  @Column(name = "is_guest", nullable = false)
  private boolean isGuest = false;

  @Column(name = "guest_token")
  private String guestToken;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  List<Appointment> appointments;

  @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  Cart cart;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id") // Đảm bảo account_id có thể NULL
  Account account;

  public User(String email, String firstName, String phoneNumber, String message) {
    super();
  }
}
