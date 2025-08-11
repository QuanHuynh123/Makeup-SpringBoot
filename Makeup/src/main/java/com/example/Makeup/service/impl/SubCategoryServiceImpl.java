/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.SubCategoryDTO;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.entity.SubCategory;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.mapper.SubCategoryMapper;
import com.example.Makeup.repository.SubCategoryRepository;
import com.example.Makeup.service.ISubCategoryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements ISubCategoryService {

  private final SubCategoryRepository subCategoryRepository;
  private final SubCategoryMapper subCategoryMapper;

  @Override
  public ApiResponse<SubCategoryDTO> findById(int id) {
    SubCategory optSubCategory =
        subCategoryRepository
            .findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.COMMON_RESOURCE_NOT_FOUND));

    return ApiResponse.success(
        "Subcategory found", subCategoryMapper.toSubCategoryDTO(optSubCategory));
  }

  @Override
  public ApiResponse<List<SubCategoryDTO>> getAll() {
    List<SubCategory> subCategories = subCategoryRepository.findAll();
    if (subCategories.isEmpty()) {
      throw new AppException(ErrorCode.COMMON_RESOURCE_NOT_FOUND);
    }

    List<SubCategoryDTO> subCategoryDTOs =
        subCategories.stream()
            .map(subCategoryMapper::toSubCategoryDTO)
            .collect(Collectors.toList());
    return ApiResponse.success("List of subcategories", subCategoryDTOs);
  }
}
