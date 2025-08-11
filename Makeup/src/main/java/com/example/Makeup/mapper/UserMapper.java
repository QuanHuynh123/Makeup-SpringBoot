package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.UserDTO;
import com.example.Makeup.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(source = "account.id", target = "accountId")
  UserDTO toUserDTO(User user);

  @Mapping(target = "account", ignore = true) // Assuming account is set separately
  User toUserEntity(UserDTO userDTO);
}
