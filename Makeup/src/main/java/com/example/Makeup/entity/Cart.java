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
@Table(name = "cart")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @Column(name = "total_price", nullable = false)
    double totalPrice ;

    @Column(name = "total_quantity", nullable = false)
    int totalQuantity;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    User user;

}
