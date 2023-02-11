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
    }

    @Test
    void listByParentId() {
    }

    @Test
    void insertOne() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(11);
        categoryDto.setName("침대");
        categoryDto.setLevel(2);
        categoryMapper.insertOne(categoryDto);
    }
}