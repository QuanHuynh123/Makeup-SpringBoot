package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.AccountDTO;
import com.example.Makeup.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

  @Mapping(source = "role.id", target = "roleId") // Lấy roleId từ role object trong entity
  AccountDTO toDTO(Account account); // Chuyển đổi từ Account sang AccountDTO

  @Mapping(source = "roleId", target = "role.id") // Lấy roleId từ role object trong entity
  Account toEntity(AccountDTO account); // Chuyển đổi từ Account sang AccountDTO
}
