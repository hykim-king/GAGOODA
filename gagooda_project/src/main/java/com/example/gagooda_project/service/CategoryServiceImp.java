package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CategoryDto;
import com.example.gagooda_project.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {
    private CategoryMapper categoryMapper;

    public CategoryServiceImp(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }


    @Override
    public List<CategoryDto> showCategoriesAt(int lvl) {
        return categoryMapper.listByLevel(lvl);
    }

    @Override
    public List<CategoryDto> showChildCategories(int parentId) {
        return categoryMapper.listByParentId(parentId);
    }
}
