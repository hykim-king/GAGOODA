package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryConnDto {
    private int categoryId; // category_id PK&FK (CATEGORY:CATEGORY_CONN) 1:N  NN 카테고리 ID

    private int productId;  // product_id PK&FK (PRODUCT:CATEGORY_CONN) 1:N  NN
}
