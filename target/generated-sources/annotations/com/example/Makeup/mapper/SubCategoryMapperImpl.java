package com.example.Makeup.mapper;

import com.example.Makeup.dto.SubCategoryDTO;
import com.example.Makeup.entity.SubCategory;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-10T18:08:00+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
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

        return subCategoryDTO;
    }

    @Override
    public SubCategory toSubCategoryEntity(SubCategoryDTO subCategoryDTO) {
        if ( subCategoryDTO == null ) {
            return null;
        }

        SubCategory subCategory = new SubCategory();

        subCategory.setId( subCategoryDTO.getId() );
        subCategory.setName( subCategoryDTO.getName() );

        return subCategory;
    }
}
