package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.entity.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserDTO(User user);

    User toUserEntity(UserDTO userDTO);
}
