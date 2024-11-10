package com.example.Makeup.mapper;

import com.example.Makeup.dto.OrderDTO;
import com.example.Makeup.entity.Order;
import com.example.Makeup.entity.Payment;
import com.example.Makeup.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-10T16:42:42+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDTO toOrderDTO(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setUserId( orderUserId( order ) );
        orderDTO.setPaymentId( orderPaymentId( order ) );
        orderDTO.setId( order.getId() );
        orderDTO.setTotalPrice( order.getTotalPrice() );
        orderDTO.setTotalQuantity( order.getTotalQuantity() );
        orderDTO.setOrderDate( order.getOrderDate() );
        orderDTO.setStatus( order.isStatus() );

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
        order.setId( orderDTO.getId() );
        order.setTotalPrice( orderDTO.getTotalPrice() );
        order.setTotalQuantity( orderDTO.getTotalQuantity() );
        order.setOrderDate( orderDTO.getOrderDate() );
        order.setStatus( orderDTO.isStatus() );

        return order;
    }

    private int orderUserId(Order order) {
        if ( order == null ) {
            return 0;
        }
        User user = order.getUser();
        if ( user == null ) {
            return 0;
        }
        int id = user.getId();
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
