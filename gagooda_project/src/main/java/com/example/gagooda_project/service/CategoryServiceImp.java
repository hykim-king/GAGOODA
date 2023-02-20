package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CategoryDto;
import com.example.gagooda_project.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public CategoryDto selectOne(int categoryId) {
        return categoryMapper.findById(categoryId);
    }

    @Override
    public List<CategoryDto> categoryMerge(List<String> categoryIdList) {
        List<CategoryDto> categoryList = new ArrayList<>();
        for (String categoryId : categoryIdList) {
            CategoryDto category = categoryMapper.findById(Integer.parseInt(categoryId));
            categoryList.add(category);
            for (CategoryDto categoryIn : categoryList) {
                if (categoryIn != category && categoryIn.getCname().equals(category.getCname())) {
                    categoryIn.setCategoryId(categoryIn.getCategoryId()+"/"+category.getCategoryId());
                    categoryList.remove(category);
                    break;
                }
            }
        }
        return categoryList;
    }
}
