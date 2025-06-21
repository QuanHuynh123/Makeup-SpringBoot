package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.ServiceMakeupDTO;
import com.example.Makeup.entity.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMakeupMapper {
    ServiceMakeupDTO toServiceMakeupDTO(ServiceMakeup serviceMakeup);

    ServiceMakeup toServiceMakeupEntity(ServiceMakeupDTO serviceMakeupDTO);
}
