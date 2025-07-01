package com.example.Makeup.entity;

import com.example.Makeup.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @Column(name = "total_price", nullable = false)
    double totalPrice  ;

    @Column(name = "total_quantity", nullable = false)
    int totalQuantity  ;

    @Column(name = "order_date",  nullable = false)
    LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "pickup_date") // ngày khách đến lấy đồ
    LocalDateTime pickupDate;

    @Column(name = "return_date")
    LocalDateTime returnDate; // để null cho đến khi khách trả đồ

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    Payment payment;

}
