package com.example.Makeup.service;

import com.example.Makeup.dto.model.SubCategoryDTO;
import com.example.Makeup.dto.response.common.ApiResponse;

import java.util.List;

public interface ISubCategoryService {

    ApiResponse<SubCategoryDTO> findById(int id);
    ApiResponse<List<SubCategoryDTO>> getAll();
}
