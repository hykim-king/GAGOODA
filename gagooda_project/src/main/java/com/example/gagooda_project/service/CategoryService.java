package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CategoryDto;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    List<CategoryDto> showCategoriesAt(int lvl);
    List<CategoryDto> showChildCategories(String parentId);
    CategoryDto selectOne(String categoryId);
    List<CategoryDto> categoryMerge(List<String> categoryIdList);
    List<CategoryDto> showAll();
    Map<String, String> categoryDict(int lvl);
}
