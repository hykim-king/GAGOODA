package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CategoryConnDto;

import java.util.List;

public interface CategoryConnService {
    List<CategoryConnDto> categoryProducts(int categoryId);
    int removeForProduct(String productCode);
}
