package com.example.Makeup.service;

import com.example.Makeup.dto.model.CategoryDTO;
import com.example.Makeup.entity.Category;
import com.example.Makeup.dto.response.common.ApiResponse;
import com.example.Makeup.exception.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.CategoryMapper;
import com.example.Makeup.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public ApiResponse<List<CategoryDTO>> getAllCategory(){
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new AppException(ErrorCode.COMMON_IS_EMPTY);
        }
        return ApiResponse.<List<CategoryDTO>>builder()
                .code(200)
                .message("Category list")
                .result(categories.stream()
                        .map(categoryMapper::toCategoryDTO)
                        .collect(Collectors.toList()))
                .build();
    }
}
