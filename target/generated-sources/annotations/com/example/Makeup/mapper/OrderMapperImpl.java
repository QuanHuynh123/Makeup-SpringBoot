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
    date = "2026-04-10T20:55:31+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
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
        LocalDateTime createdAt = null;
        LocalDateTime orderDate = null;
        LocalDateTime pickupDate = null;
        String receiverAddress = null;
        String receiverEmail = null;
        String receiverMessage = null;
        String receiverName = null;
        String receiverPhone = null;
        LocalDateTime returnDate = null;
        OrderStatus status = null;
        double totalPrice = 0.0d;
        int totalQuantity = 0;
        ShippingType typeShipping = null;
        String uniqueRequestId = null;
        LocalDateTime updatedAt = null;
        UUID id = null;

        userId = orderUserId( order );
        paymentId = orderPaymentId( order );
        createdAt = order.getCreatedAt();
        orderDate = order.getOrderDate();
        pickupDate = order.getPickupDate();
        receiverAddress = order.getReceiverAddress();
        receiverEmail = order.getReceiverEmail();
        receiverMessage = order.getReceiverMessage();
        receiverName = order.getReceiverName();
        receiverPhone = order.getReceiverPhone();
        returnDate = order.getReturnDate();
        status = order.getStatus();
        totalPrice = order.getTotalPrice();
        totalQuantity = order.getTotalQuantity();
        typeShipping = order.getTypeShipping();
        uniqueRequestId = order.getUniqueRequestId();
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
        order.setOrderDate( orderDTO.getOrderDate() );
        order.setPickupDate( orderDTO.getPickupDate() );
        order.setReceiverAddress( orderDTO.getReceiverAddress() );
        order.setReceiverEmail( orderDTO.getReceiverEmail() );
        order.setReceiverMessage( orderDTO.getReceiverMessage() );
        order.setReceiverName( orderDTO.getReceiverName() );
        order.setReceiverPhone( orderDTO.getReceiverPhone() );
        order.setReturnDate( orderDTO.getReturnDate() );
        order.setStatus( orderDTO.getStatus() );
        order.setTotalPrice( orderDTO.getTotalPrice() );
        order.setTotalQuantity( orderDTO.getTotalQuantity() );
        order.setTypeShipping( orderDTO.getTypeShipping() );
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
