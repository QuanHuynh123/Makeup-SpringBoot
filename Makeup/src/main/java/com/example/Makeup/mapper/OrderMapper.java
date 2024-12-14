package com.example.Makeup.mapper;

import com.example.Makeup.dto.*;
import com.example.Makeup.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface OrderMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "payment.id", target = "paymentId")
    @Mapping(source = "user.phone", target = "phone")
    @Mapping(source = "user.fullName", target = "name")
    OrderDTO toOrderDTO(Order order);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "paymentId", target = "payment.id")
    Order toOrderEntity(OrderDTO orderDTO);
}
