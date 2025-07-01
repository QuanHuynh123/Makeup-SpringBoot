package com.example.Makeup.service;

import com.example.Makeup.dto.model.CategoryDTO;
import com.example.Makeup.dto.response.common.ApiResponse;

import java.util.List;

public interface ICategoryService {

    ApiResponse<List<CategoryDTO>> getAllCategory();
}
