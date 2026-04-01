package com.example.Makeup.mapper;

import com.example.Makeup.dto.model.CategoryDTO;
import com.example.Makeup.dto.model.SubCategoryDTO;
import com.example.Makeup.entity.Category;
import com.example.Makeup.entity.SubCategory;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-21T00:11:26+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Autowired
    private SubCategoryMapper subCategoryMapper;

    @Override
    public CategoryDTO toCategoryDTO(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setCreatedAt( category.getCreatedAt() );
        categoryDTO.setId( category.getId() );
        categoryDTO.setName( category.getName() );
        categoryDTO.setSubCategories( subCategoryListToSubCategoryDTOList( category.getSubCategories() ) );
        categoryDTO.setUpdatedAt( category.getUpdatedAt() );

        return categoryDTO;
    }

    @Override
    public Category toCategoryEntity(CategoryDTO categoryDTO) {
        if ( categoryDTO == null ) {
            return null;
        }

        Category category = new Category();

        category.setCreatedAt( categoryDTO.getCreatedAt() );
        category.setUpdatedAt( categoryDTO.getUpdatedAt() );
        category.setId( categoryDTO.getId() );
        category.setName( categoryDTO.getName() );
        category.setSubCategories( subCategoryDTOListToSubCategoryList( categoryDTO.getSubCategories() ) );

        return category;
    }

    protected List<SubCategoryDTO> subCategoryListToSubCategoryDTOList(List<SubCategory> list) {
        if ( list == null ) {
            return null;
        }

        List<SubCategoryDTO> list1 = new ArrayList<SubCategoryDTO>( list.size() );
        for ( SubCategory subCategory : list ) {
            list1.add( subCategoryMapper.toSubCategoryDTO( subCategory ) );
        }

        return list1;
    }

    protected List<SubCategory> subCategoryDTOListToSubCategoryList(List<SubCategoryDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<SubCategory> list1 = new ArrayList<SubCategory>( list.size() );
        for ( SubCategoryDTO subCategoryDTO : list ) {
            list1.add( subCategoryMapper.toSubCategoryEntity( subCategoryDTO ) );
        }

        return list1;
    }
}
