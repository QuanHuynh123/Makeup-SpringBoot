package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.SubCategoryDTO;
import com.example.Makeup.entity.SubCategory;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-19T14:14:35+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class SubCategoryMapperImpl implements SubCategoryMapper {

    @Override
    public SubCategoryDTO toSubCategoryDTO(SubCategory subCategory) {
        if ( subCategory == null ) {
            return null;
        }

        SubCategoryDTO subCategoryDTO = new SubCategoryDTO();

        subCategoryDTO.setId( subCategory.getId() );
        subCategoryDTO.setName( subCategory.getName() );
        if ( subCategory.getStatus() != null ) {
            subCategoryDTO.setStatus( subCategory.getStatus() );
        }
        subCategoryDTO.setCreatedAt( subCategory.getCreatedAt() );
        subCategoryDTO.setUpdatedAt( subCategory.getUpdatedAt() );

        return subCategoryDTO;
    }

    @Override
    public SubCategory toSubCategoryEntity(SubCategoryDTO subCategoryDTO) {
        if ( subCategoryDTO == null ) {
            return null;
        }

        SubCategory subCategory = new SubCategory();

        subCategory.setCreatedAt( subCategoryDTO.getCreatedAt() );
        subCategory.setUpdatedAt( subCategoryDTO.getUpdatedAt() );
        subCategory.setId( subCategoryDTO.getId() );
        subCategory.setName( subCategoryDTO.getName() );
        subCategory.setStatus( subCategoryDTO.isStatus() );

        return subCategory;
    }
}
