package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CategoryConnDto;

import java.util.List;
import java.util.Map;

public interface CategoryConnService {
    List<CategoryConnDto> categoryProducts(int categoryId);
    int removeForProduct(String productCode);
    List<CategoryConnDto> showForProduct(String productCode);
}
