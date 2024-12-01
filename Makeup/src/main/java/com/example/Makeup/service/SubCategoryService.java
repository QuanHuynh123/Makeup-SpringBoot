/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Makeup.service;

import com.example.Makeup.dto.SubCategoryDTO;
import com.example.Makeup.entity.SubCategory;
import com.example.Makeup.enums.AppException;
import com.example.Makeup.enums.ErrorCode;
import com.example.Makeup.mapper.SubCategoryMapper;
import com.example.Makeup.repository.SubCategoryRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubCategoryService {
    @Autowired
    SubCategoryRepository subCategoryRepository;

    @Autowired
    SubCategoryMapper subCategoryMapper;

    public SubCategoryDTO findById(int id){
        // Dùng orElseThrow để ném ngoại lệ nếu không tìm thấy
        SubCategory optSubCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Tiến hành xử lý
        return subCategoryMapper.toSubCategoryDTO(optSubCategory);
    }

}
