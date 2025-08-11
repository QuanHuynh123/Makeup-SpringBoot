package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.StaffDTO;
import com.example.Makeup.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StaffMapper {

  @Mapping(source = "account.id", target = "accountId")
  StaffDTO toStaffDTO(Staff staff);

  @Mapping(source = "accountId", target = "account.id")
  Staff toStaffEntity(StaffDTO staffDTO);
}
