package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.StaffDTO;
import com.example.Makeup.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StaffMapper {
    StaffMapper INSTANCE = Mappers.getMapper(StaffMapper.class);

    // Chuyển đổi từ Staff sang StaffDTO
    @Mappings({
            @Mapping(source = "account.id", target = "accountId") // Ánh xạ accountId từ entity sang DTO
    })
    StaffDTO toStaffDTO(Staff staff);

    // Chuyển đổi từ StaffDTO sang Staff
    @Mappings({
            @Mapping(source = "accountId", target = "account.id") // Ánh xạ accountId từ DTO sang entity
    })
    Staff toStaffEntity(StaffDTO staffDTO);

    // Phương thức chuyển đổi danh sách
    List<StaffDTO> toStaffDTOList(List<Staff> staffList);
}