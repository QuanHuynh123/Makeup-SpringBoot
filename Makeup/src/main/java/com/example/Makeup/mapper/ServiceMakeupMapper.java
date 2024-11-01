package com.example.Makeup.mapper;

import com.example.Makeup.dto.*;
import com.example.Makeup.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ServiceMakeupMapper {
    ServiceMakeupDTO toServiceMakeupDTO(ServiceMakeup serviceMakeup);

    ServiceMakeup toServiceMakeupEntity(ServiceMakeupDTO serviceMakeupDTO);
}
