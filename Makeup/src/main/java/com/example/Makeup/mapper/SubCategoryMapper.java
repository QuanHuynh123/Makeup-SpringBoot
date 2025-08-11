package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.SubCategoryDTO;
import com.example.Makeup.entity.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubCategoryMapper {
  SubCategoryDTO toSubCategoryDTO(SubCategory subCategory);

  SubCategory toSubCategoryEntity(SubCategoryDTO subCategoryDTO);
}
