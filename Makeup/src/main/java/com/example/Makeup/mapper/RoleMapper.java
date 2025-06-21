package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.RoleDTO;
import com.example.Makeup.entity.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDTO toRoleDTO(Role role);

    Role toRoleEntity(RoleDTO roleDTO);
}
