package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.RoleDTO;
import com.example.Makeup.entity.Role;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-20T22:10:21+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public RoleDTO toRoleDTO(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setId( role.getId() );
        roleDTO.setNameRole( role.getNameRole() );
        roleDTO.setCreatedAt( role.getCreatedAt() );
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
