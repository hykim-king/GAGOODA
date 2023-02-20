package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.CategoryConnDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryConnMapperTest {

    @Autowired
    CategoryConnMapper categoryConnMapper;

    @Test
    void listByCategoryId() {
        categoryConnMapper.listByCategoryId(511);
    }

    @Test
    void listByProductCode() {
        categoryConnMapper.listByProductCode("PDT001");
    }

    @Test
    void insertOne() {
        CategoryConnDto categoryConn = new CategoryConnDto();
        categoryConn.setCategoryId("511");
        categoryConn.setProductCode("PDT001");
        categoryConnMapper.insertOne(categoryConn);
    }

    @Test
    void deleteByProductCode() {
        categoryConnMapper.deleteByProductCode("PDT001");
    }
}