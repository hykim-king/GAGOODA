package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private int categoryId;         // category_id (카테고리 ID) , PK , NN , Autoincrement

    private int higherLevelId;      // higher_level_id (상위 카테고리 ID) , FK

    private String name;            // name (카테고리 이름) , NN

    private int level;             // level (카테고리 층) , NN
}
