package com.example.gagooda_project.dto;

import lombok.Data;

@Data
public class CartDto {
    private int cartId;    // 카트 아이디 cart_id PK NN
    private int count;     // 수량 count NN
    private String optionId; // 옵션 상품 아이디 option_id FK NN ( CART : OPTION_PRODUCT ) N:1
    private String productId; // 상품 아이디 product_id FK NN ( CART : PRODUCT ) N:1
    private int userId;        // 유저 아이디 user_id FK NN ( CART : USER ) N:1
}
