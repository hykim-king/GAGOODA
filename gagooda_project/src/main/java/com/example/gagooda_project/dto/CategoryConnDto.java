package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryConnDto {
    private int categoryConnId;     // category_conn_id (상품 카테고리 연결 ID) , PK , NN , Autoincrement

    private int categoryId;         // category_id (카테고리 ID) , FK , NN

    private int productId;          // product_id (상품 ID) , FK , NN
}
