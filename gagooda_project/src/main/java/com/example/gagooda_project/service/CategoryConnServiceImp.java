package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CategoryConnDto;
import com.example.gagooda_project.dto.ProductDto;
import com.example.gagooda_project.mapper.CategoryConnMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public int removeForProduct(String productCode) {
        return categoryConnMapper.deleteByProductCode(productCode);
    }

    @Override
    public List<CategoryConnDto> showForProduct(String productCode) {
        return categoryConnMapper.listByProductCode(productCode);
    }
}
