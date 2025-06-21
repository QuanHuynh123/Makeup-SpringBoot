/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Makeup.service;

import com.example.Makeup.dto.model.SubCategoryDTO;
import com.example.Makeup.entity.SubCategory;
import com.example.Makeup.enums.ApiResponse;
import com.example.Makeup.enums.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.SubCategoryMapper;
import com.example.Makeup.repository.SubCategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryMapper subCategoryMapper;

    public ApiResponse<SubCategoryDTO> findById(int id){
        SubCategory optSubCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CANT_FOUND));

        return ApiResponse.<SubCategoryDTO>builder()
                .code(200)
                .message("Subcategory found")
                .result(subCategoryMapper.toSubCategoryDTO(optSubCategory))
                .build();
    }

    public ApiResponse<List<SubCategoryDTO>> getAll(){
        List<SubCategory> subCategories = subCategoryRepository.findAll();
        if (subCategories.isEmpty()) {
            throw new AppException(ErrorCode.IS_EMPTY);
        }
        return ApiResponse.<List<SubCategoryDTO>>builder()
                .code(200)
                .message("Subcategories found")
                .result(subCategories.stream().map(subCategoryMapper::toSubCategoryDTO).collect(Collectors.toList()))
                .build();
    }

}
