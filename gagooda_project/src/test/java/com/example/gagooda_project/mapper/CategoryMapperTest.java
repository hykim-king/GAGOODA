package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.CategoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryMapperTest {
    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    void findById() {
        categoryMapper.findById(1);
    }

    @Test
    void listByParentId() {
        categoryMapper.listByParentId(1);
    }

    @Test
    void insertOne() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(11);
        categoryDto.setCname("침대");
        categoryDto.setLvl(2);
        categoryMapper.insertOne(categoryDto);
    }
}