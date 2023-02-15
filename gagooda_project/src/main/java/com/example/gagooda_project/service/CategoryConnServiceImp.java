package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CategoryConnDto;
import com.example.gagooda_project.mapper.CategoryConnMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryConnServiceImp implements CategoryConnService {
    private CategoryConnMapper categoryConnMapper;

    public CategoryConnServiceImp(CategoryConnMapper categoryConnMapper) {
        this.categoryConnMapper = categoryConnMapper;
    }

    @Override
    public List<CategoryConnDto> categoryProducts(int categoryId) {
        return categoryConnMapper.listByCategoryId(categoryId);
    }
}
