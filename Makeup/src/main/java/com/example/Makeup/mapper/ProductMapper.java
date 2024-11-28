package com.example.Makeup.mapper;

import com.example.Makeup.dto.*;
import com.example.Makeup.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring")

public interface ProductMapper {

    @Mapping(source = "subCategory.id", target = "subCategoryId")
    ProductDTO toProductDTO(Product product);

    @Mapping(source = "subCategoryId", target = "subCategory.id")
    Product toProductEntity(ProductDTO productDTO);

}
