package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.OrderItemDTO;
import com.example.Makeup.entity.Order;
import com.example.Makeup.entity.OrderItem;
import com.example.Makeup.entity.Product;
import com.example.Makeup.enums.OrderItemStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-21T00:11:26+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class OrderItemMapperImpl implements OrderItemMapper {

    @Override
    public OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }

        UUID orderId = null;
        UUID productId = null;
        LocalDateTime createdAt = null;
        double price = 0.0d;
        int quantity = 0;
        LocalDateTime rentalDate = null;
        OrderItemStatus status = null;
        LocalDateTime updatedAt = null;
        UUID id = null;

        orderId = orderItemOrderId( orderItem );
        productId = orderItemProductId( orderItem );
        createdAt = orderItem.getCreatedAt();
        price = orderItem.getPrice();
        quantity = orderItem.getQuantity();
        rentalDate = orderItem.getRentalDate();
        status = orderItem.getStatus();
        updatedAt = orderItem.getUpdatedAt();
        id = orderItem.getId();

        OrderItemDTO orderItemDTO = new OrderItemDTO( id, quantity, price, status, rentalDate, orderId, productId, createdAt, updatedAt );

        return orderItemDTO;
    }

    @Override
    public OrderItem toOrderItemEntity(OrderItemDTO orderItemDTO) {
        if ( orderItemDTO == null ) {
            return null;
        }

        OrderItem orderItem = new OrderItem();

        orderItem.setOrder( orderItemDTOToOrder( orderItemDTO ) );
        orderItem.setProduct( orderItemDTOToProduct( orderItemDTO ) );
        orderItem.setCreatedAt( orderItemDTO.getCreatedAt() );
        orderItem.setUpdatedAt( orderItemDTO.getUpdatedAt() );
        orderItem.setId( orderItemDTO.getId() );
        orderItem.setPrice( orderItemDTO.getPrice() );
        orderItem.setQuantity( orderItemDTO.getQuantity() );
        orderItem.setRentalDate( orderItemDTO.getRentalDate() );
        orderItem.setStatus( orderItemDTO.getStatus() );

        return orderItem;
    }

    private UUID orderItemOrderId(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }
        Order order = orderItem.getOrder();
        if ( order == null ) {
            return null;
        }
        UUID id = order.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private UUID orderItemProductId(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }
        Product product = orderItem.getProduct();
        if ( product == null ) {
            return null;
        }
        UUID id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Order orderItemDTOToOrder(OrderItemDTO orderItemDTO) {
        if ( orderItemDTO == null ) {
            return null;
        }

        Order order = new Order();

        order.setId( orderItemDTO.getOrderId() );

        return order;
    }

    protected Product orderItemDTOToProduct(OrderItemDTO orderItemDTO) {
        if ( orderItemDTO == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( orderItemDTO.getProductId() );

        return product;
    }
}
