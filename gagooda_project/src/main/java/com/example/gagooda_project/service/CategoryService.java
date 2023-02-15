package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> showCategoriesAt(int lvl);
    List<CategoryDto> showChildCategories(int parentId);
}
