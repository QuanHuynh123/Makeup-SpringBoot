package com.example.Makeup.mapper;

import com.example.Makeup.dto.*;
import com.example.Makeup.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface OrderMapper {
    @Mapping(source = "user.id", target = "userId")
    OrderDTO toOrderDTO(Order order);

    @Mapping(source = "userId", target = "user.id")
    Order toOrderEntity(OrderDTO orderDTO);
}
