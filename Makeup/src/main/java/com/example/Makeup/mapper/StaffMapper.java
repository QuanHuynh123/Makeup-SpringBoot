package com.example.Makeup.mapper;

import com.example.Makeup.dto.*;
import com.example.Makeup.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StaffMapper {
    StaffMapper INSTANCE = Mappers.getMapper(StaffMapper.class);

    StaffDTO toStaffDTO(Staff staff);

    Staff toStaffEntity(StaffDTO staffDTO);

    // Phương thức chuyển đổi danh sách
    List<StaffDTO> toStaffDTOList(List<Staff> staffList);
}
