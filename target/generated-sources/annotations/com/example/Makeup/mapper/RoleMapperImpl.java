package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.RoleDTO;
import com.example.Makeup.entity.Role;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-10T20:55:31+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public RoleDTO toRoleDTO(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setCreatedAt( role.getCreatedAt() );
        roleDTO.setId( role.getId() );
        roleDTO.setNameRole( role.getNameRole() );
        roleDTO.setUpdatedAt( role.getUpdatedAt() );

        return roleDTO;
    }

    @Override
    public Role toRoleEntity(RoleDTO roleDTO) {
        if ( roleDTO == null ) {
            return null;
        }

        Role role = new Role();

        role.setCreatedAt( roleDTO.getCreatedAt() );
        role.setUpdatedAt( roleDTO.getUpdatedAt() );
        role.setId( roleDTO.getId() );
        role.setNameRole( roleDTO.getNameRole() );

        return role;
    }
}
