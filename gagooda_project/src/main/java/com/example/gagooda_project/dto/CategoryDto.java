package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private int categoryId;     // category_id PK NN 카테고리 ID Auto_Increment
    private int parentId;       // parent_id FK NN (parentCategory: Category) 1:N 상위 카테고리 ID Self-Join

    private String name;        // name NN 카테고리 이름
    private int level;          // level NN 카테고리 층
}
