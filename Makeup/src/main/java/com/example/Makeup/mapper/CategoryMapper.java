package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.CategoryDTO;
import com.example.Makeup.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" ,uses = SubCategoryMapper.class)

public interface CategoryMapper {

    CategoryDTO toCategoryDTO(Category category);

    Category toCategoryEntity(CategoryDTO categoryDTO);
}
