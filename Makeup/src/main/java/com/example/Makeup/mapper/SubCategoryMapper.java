package com.example.Makeup.mapper;

import com.example.Makeup.dto.*;
import com.example.Makeup.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SubCategoryMapper {
    SubCategoryDTO toSubCategoryDTO(SubCategory subCategory);

    SubCategory toSubCategoryEntity(SubCategoryDTO subCategoryDTO);

}
