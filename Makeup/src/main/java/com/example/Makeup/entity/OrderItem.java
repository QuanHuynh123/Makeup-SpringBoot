package com.example.Makeup.entity;

import com.example.Makeup.enums.OrderItemStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "order_item")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @Column(name = "quantity", nullable = false)
    int quantity  ;

    @Column(name = "price",  nullable = false)
    double price;

    @Column(name = "rental_date", nullable = false) // ngày bắt đầu thuê
    LocalDateTime rentalDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    OrderItemStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    Product product;
}
