package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDto {
    private int cartId;    // 카트 아이디 cart_id PK NN
    private String optionCode; // 옵션 상품 아이디 option_code FK NN ( CART : OPTION_PRODUCT ) N:1
    private String productCode; // 상품 아이디 product_code FK NN ( CART : PRODUCT ) N:1
    private int userId;        // 유저 아이디 user_id FK NN ( CART : USER ) N:1
    private int cnt;     // 수량 count NN
    OptionProductDto optionProduct;
    ProductDto product;
    List<ImageDto> imageList;
}
