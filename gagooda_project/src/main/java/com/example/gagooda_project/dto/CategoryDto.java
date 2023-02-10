package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private int categoryId;     //category_id PK NN 카테고리 ID

    private int parentId;       // parent_id FK (CATEGORY:PARENT) N:1 NN 상위 카테고리 ID

    private String name;        // name NN 카테고리 이름

    private int level;          // leve NN 카테고리 층

}
