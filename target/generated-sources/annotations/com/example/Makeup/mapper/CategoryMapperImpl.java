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
    date = "2025-10-20T22:10:21+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
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

        categoryDTO.setId( category.getId() );
        categoryDTO.setName( category.getName() );
        categoryDTO.setSubCategories( subCategoryListToSubCategoryDTOList( category.getSubCategories() ) );
        categoryDTO.setCreatedAt( category.getCreatedAt() );
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
