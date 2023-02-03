package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private int categoryId;         // 카테고리 ID

    private int higherLevelId;      // 상위 카테고리 ID

    private String name;            // 카테고리 이름

    private int lvl;             // 카테고리 층

}
