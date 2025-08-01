package com.example.Makeup.entity;

import com.example.Makeup.enums.OrderStatus;
import com.example.Makeup.enums.ShippingType;
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

    @Column(name = "pickup_date") // ngày khách đến lấy đồ
    LocalDateTime pickupDate;

    @Column(name = "return_date")
    LocalDateTime returnDate; // để null cho đến khi khách trả đồ

    @Column(name = "receiver_name", length = 40)
    String receiverName;

    @Column(name = "receiver_email", length = 100)
    String receiverEmail;

    @Column(name = "receiver_phone", length = 20)
    String receiverPhone;

    @Column(name = "receiver_message", length = 200)
    String receiverMessage;

    @Column(name = "receiver_address", length = 200)
    String receiverAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_shipping", nullable = false)
    ShippingType typeShipping;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

}
