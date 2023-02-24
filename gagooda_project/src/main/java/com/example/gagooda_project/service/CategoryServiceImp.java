package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CategoryDto;
import com.example.gagooda_project.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, String> categoryDict(int lvl) {
        List<CategoryDto> categoryList = categoryMapper.listByLevel(lvl);
        Map<String, String> map = new HashMap<>();
        for(CategoryDto category: categoryList) {
            map.put(category.getCategoryId(), category.getCname());
        }
        return map;
    }

    @Override
    public List<CategoryDto> showChildCategories(String parentId) {
        return categoryMapper.listByParentId(parentId);
    }

    @Override
    public List<CategoryDto> showAll() {
        return categoryMapper.listAll();
    }

    @Override
    public CategoryDto selectOne(String categoryId) {
        return categoryMapper.findById(categoryId);
    }

    @Override
    public List<CategoryDto> categoryMerge(List<String> categoryIdList) {
        List<CategoryDto> categoryList = new ArrayList<>();
        for (String categoryId : categoryIdList) {
            CategoryDto category = categoryMapper.findById(categoryId);
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
