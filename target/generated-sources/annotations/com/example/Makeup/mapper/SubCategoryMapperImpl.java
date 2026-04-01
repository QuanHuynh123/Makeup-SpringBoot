package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.SubCategoryDTO;
import com.example.Makeup.entity.SubCategory;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-21T00:11:26+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class SubCategoryMapperImpl implements SubCategoryMapper {

    @Override
    public SubCategoryDTO toSubCategoryDTO(SubCategory subCategory) {
        if ( subCategory == null ) {
            return null;
        }

        SubCategoryDTO subCategoryDTO = new SubCategoryDTO();

        subCategoryDTO.setCreatedAt( subCategory.getCreatedAt() );
        subCategoryDTO.setId( subCategory.getId() );
        subCategoryDTO.setName( subCategory.getName() );
        if ( subCategory.getStatus() != null ) {
            subCategoryDTO.setStatus( subCategory.getStatus() );
        }
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
