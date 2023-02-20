package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductMapperTest {
    @Autowired
    ProductMapper productMapper;

    @Test
    void mainListBySales() {
        System.out.println(productMapper.mainListBySales("GAGOODA"));
    }
}