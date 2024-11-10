package com.example.Makeup.mapper;

import com.example.Makeup.dto.OrderItemDTO;
import com.example.Makeup.entity.Order;
import com.example.Makeup.entity.OrderItem;
import com.example.Makeup.entity.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-10T18:08:01+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class OrderItemMapperImpl implements OrderItemMapper {

    @Override
    public OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }

        OrderItemDTO orderItemDTO = new OrderItemDTO();

        orderItemDTO.setOrderId( orderItemOrderId( orderItem ) );
        orderItemDTO.setProductId( orderItemProductId( orderItem ) );
        orderItemDTO.setId( orderItem.getId() );
        orderItemDTO.setQuantity( orderItem.getQuantity() );
        orderItemDTO.setPrice( orderItem.getPrice() );
        orderItemDTO.setUseDate( orderItem.getUseDate() );

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
        orderItem.setId( orderItemDTO.getId() );
        orderItem.setQuantity( orderItemDTO.getQuantity() );
        orderItem.setPrice( orderItemDTO.getPrice() );
        orderItem.setUseDate( orderItemDTO.getUseDate() );

        return orderItem;
    }

    private int orderItemOrderId(OrderItem orderItem) {
        if ( orderItem == null ) {
            return 0;
        }
        Order order = orderItem.getOrder();
        if ( order == null ) {
            return 0;
        }
        int id = order.getId();
        return id;
    }

    private int orderItemProductId(OrderItem orderItem) {
        if ( orderItem == null ) {
            return 0;
        }
        Product product = orderItem.getProduct();
        if ( product == null ) {
            return 0;
        }
        int id = product.getId();
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
