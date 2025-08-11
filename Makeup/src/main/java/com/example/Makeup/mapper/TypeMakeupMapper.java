package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.TypeMakeupDTO;
import com.example.Makeup.entity.TypeMakeup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeMakeupMapper {

  TypeMakeupDTO toTypeMakeupDTO(TypeMakeup typeMakeup);

  TypeMakeup toTypeMakeupEntity(TypeMakeupDTO typeMakeupDTO);
}
