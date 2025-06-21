package com.example.Makeup.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "account")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @Column(name = "username", length = 250, nullable = false,unique = true)
    String userName;

    @Column(name = "password", length = 250, nullable = false)
    String passWord;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    User user;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    Staff staff;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    Role role;

}
