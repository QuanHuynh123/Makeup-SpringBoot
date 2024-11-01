package com.example.Makeup.mapper;

import com.example.Makeup.dto.*;
import com.example.Makeup.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface CartMapper {
    @Mapping(source = "user.id", target = "userId")
    CartDTO toCartDTO(Cart cart);

    @Mapping(source = "userId", target = "user.id")
    Cart toCartEntity(CartDTO cartDTO);
}
