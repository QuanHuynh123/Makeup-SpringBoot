package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.OrderDTO;
import com.example.Makeup.entity.Order;
import com.example.Makeup.entity.Payment;
import com.example.Makeup.entity.User;
import com.example.Makeup.enums.OrderStatus;
import com.example.Makeup.enums.ShippingType;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-28T12:21:36+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDTO toOrderDTO(Order order) {
        if ( order == null ) {
            return null;
        }

        UUID userId = null;
        int paymentId = 0;
        double totalPrice = 0.0d;
        int totalQuantity = 0;
        LocalDateTime orderDate = null;
        LocalDateTime pickupDate = null;
        LocalDateTime returnDate = null;
        OrderStatus status = null;
        String uniqueRequestId = null;
        String receiverName = null;
        String receiverEmail = null;
        String receiverPhone = null;
        String receiverMessage = null;
        String receiverAddress = null;
        ShippingType typeShipping = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;
        UUID id = null;

        userId = orderUserId( order );
        paymentId = orderPaymentId( order );
        totalPrice = order.getTotalPrice();
        totalQuantity = order.getTotalQuantity();
        orderDate = order.getOrderDate();
        pickupDate = order.getPickupDate();
        returnDate = order.getReturnDate();
        status = order.getStatus();
        uniqueRequestId = order.getUniqueRequestId();
        receiverName = order.getReceiverName();
        receiverEmail = order.getReceiverEmail();
        receiverPhone = order.getReceiverPhone();
        receiverMessage = order.getReceiverMessage();
        receiverAddress = order.getReceiverAddress();
        typeShipping = order.getTypeShipping();
        createdAt = order.getCreatedAt();
        updatedAt = order.getUpdatedAt();
        id = order.getId();

        boolean returnStatus = false;

        OrderDTO orderDTO = new OrderDTO( id, totalPrice, totalQuantity, orderDate, pickupDate, returnDate, status, userId, paymentId, returnStatus, uniqueRequestId, receiverName, receiverEmail, receiverPhone, receiverMessage, receiverAddress, typeShipping, createdAt, updatedAt );

        return orderDTO;
    }

    @Override
    public Order toOrderEntity(OrderDTO orderDTO) {
        if ( orderDTO == null ) {
            return null;
        }

        Order order = new Order();

        order.setUser( orderDTOToUser( orderDTO ) );
        order.setPayment( orderDTOToPayment( orderDTO ) );
        order.setCreatedAt( orderDTO.getCreatedAt() );
        order.setUpdatedAt( orderDTO.getUpdatedAt() );
        order.setId( orderDTO.getId() );
        order.setTotalPrice( orderDTO.getTotalPrice() );
        order.setTotalQuantity( orderDTO.getTotalQuantity() );
        order.setOrderDate( orderDTO.getOrderDate() );
        order.setPickupDate( orderDTO.getPickupDate() );
        order.setReturnDate( orderDTO.getReturnDate() );
        order.setReceiverName( orderDTO.getReceiverName() );
        order.setReceiverEmail( orderDTO.getReceiverEmail() );
        order.setReceiverPhone( orderDTO.getReceiverPhone() );
        order.setReceiverMessage( orderDTO.getReceiverMessage() );
        order.setReceiverAddress( orderDTO.getReceiverAddress() );
        order.setTypeShipping( orderDTO.getTypeShipping() );
        order.setStatus( orderDTO.getStatus() );
        order.setUniqueRequestId( orderDTO.getUniqueRequestId() );

        return order;
    }

    private UUID orderUserId(Order order) {
        if ( order == null ) {
            return null;
        }
        User user = order.getUser();
        if ( user == null ) {
            return null;
        }
        UUID id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private int orderPaymentId(Order order) {
        if ( order == null ) {
            return 0;
        }
        Payment payment = order.getPayment();
        if ( payment == null ) {
            return 0;
        }
        int id = payment.getId();
        return id;
    }

    protected User orderDTOToUser(OrderDTO orderDTO) {
        if ( orderDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( orderDTO.getUserId() );

        return user;
    }

    protected Payment orderDTOToPayment(OrderDTO orderDTO) {
        if ( orderDTO == null ) {
            return null;
        }

        Payment payment = new Payment();

        payment.setId( orderDTO.getPaymentId() );

        return payment;
    }
}
