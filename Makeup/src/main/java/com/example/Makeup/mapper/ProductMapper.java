package com.example.Makeup.mapper;

import com.example.Makeup.dto.*;
import com.example.Makeup.entity.*;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Optional;

@Mapper(componentModel = "spring")

public interface ProductMapper {

    @Mapping(source = "subCategory.id", target = "subCategoryId")
    ProductDTO toProductDTO(Product product);

    @Mapping(source = "subCategoryId", target = "subCategory.id")
    Product toProductEntity(ProductDTO productDTO);

    // Logic để xử lý thuộc tính `firstImage`
    @AfterMapping
    default void setFirstImage(@MappingTarget ProductDTO productDTO, Product product) {
        if (product.getImage() != null && !product.getImage().isEmpty()) {
            // Tách chuỗi image theo dấu ','
            String[] images = product.getImage().split(",");
            // Lấy giá trị đầu tiên
            productDTO.setFirstImage(images[0].trim());
        }
    }

}
