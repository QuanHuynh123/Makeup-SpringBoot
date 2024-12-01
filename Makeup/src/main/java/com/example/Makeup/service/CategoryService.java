package com.example.Makeup.service;

import com.example.Makeup.dto.CategoryDTO;
import com.example.Makeup.entity.Category;
import com.example.Makeup.mapper.CategoryMapper;
import com.example.Makeup.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryMapper categoryMapper;
    public List<CategoryDTO> getAllCategory(){
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::toCategoryDTO)
                .collect(Collectors.toList());
    }
}
