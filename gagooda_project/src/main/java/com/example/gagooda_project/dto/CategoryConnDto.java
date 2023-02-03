package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryConnDto {
    private int categoryConnId;     // 상품 카테고리 연결 ID

    private List<CategoryDto> categoryId;         // 카테고리 ID

    private int productId;          // 상품 ID
}
