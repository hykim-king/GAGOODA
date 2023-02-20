package com.example.gagooda_project.dto;

import lombok.Data;

@Data
public class CategoryDto {
    private String categoryId;     // category_id PK NN 카테고리 ID Auto_Increment
    private String parentId;       // parent_id FK (parentCategory: Category) 1:N 상위 카테고리 ID Self-Join
    private String cname;        // name NN 카테고리 이름
    private int lvl;          // lvl NN 카테고리 층
}
