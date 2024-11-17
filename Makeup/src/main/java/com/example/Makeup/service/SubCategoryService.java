/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Makeup.service;

import com.example.Makeup.entity.SubCategory;
import com.example.Makeup.repository.SubCategoryRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Asus
 */
@Service
public class SubCategoryService {
    @Autowired
    SubCategoryRepository subCategoryRepository;
    
    public SubCategory findById(int id){
        Optional<SubCategory> optSubCategory = subCategoryRepository.findById(id);
        if (optSubCategory.isEmpty()) {
            return null;
        }
        
        return optSubCategory.get();
    }
}
