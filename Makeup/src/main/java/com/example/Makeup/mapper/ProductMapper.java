package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.ProductDTO;
import com.example.Makeup.entity.*;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface ProductMapper {

    @Mapping(source = "subCategory.id", target = "subCategoryId")
    @Mapping(source = "description", target = "describe")
    ProductDTO toProductDTO(Product product);

    @Mapping(source = "subCategoryId", target = "subCategory.id")
    @Mapping(source = "describe", target = "description")
    Product toProductEntity(ProductDTO productDTO);

}
