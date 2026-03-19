package com.example.Makeup.service.impl;

import com.example.Makeup.dto.model.CategoryDTO;
import com.example.Makeup.mapper.CategoryMapper;
import com.example.Makeup.repository.CategoryRepository;
import com.example.Makeup.service.ICategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Override
  public List<CategoryDTO> getAllCategory() {
    return categoryRepository.findAll().stream().map(categoryMapper::toCategoryDTO).toList();
  }
}
