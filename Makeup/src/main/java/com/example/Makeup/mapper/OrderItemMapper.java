package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.OrderItemDTO;
import com.example.Makeup.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "product.id", target = "productId")
    OrderItemDTO toOrderItemDTO(OrderItem orderItem);

    @Mapping(source = "orderId", target = "order.id")
    @Mapping(source = "productId", target = "product.id")
    OrderItem toOrderItemEntity(OrderItemDTO orderItemDTO);
}
