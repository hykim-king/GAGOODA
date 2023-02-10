package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.List;

@Data
class OptionProductDto {
    private String optionId; // option_id PK NN 옵션상품 ID AUTO_INCREMENT
    private String productId; //product_id PK&FK (PRODUCT:OPTION_PRODUCT) 1:N NN 상품 ID
    private String name; //name NN 옵션 상품 이름
    private int price; //price NN 상품 가격
    private int stock; //stock NN 총 수량 DEFAULT 0
}

