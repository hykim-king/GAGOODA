package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.List;

@Data
public class OptionProductDto {
    private String optionCode; // option_code PK NN 옵션상품 ID AUTO_INCREMENT
    private String productCode; //product_code PK&FK (PRODUCT:OPTION_PRODUCT) 1:N NN 상품 ID
    private String opname; //name NN 옵션 상품 이름
    private int price; //price NN 상품 가격
    private int stock; //stock NN 총 수량 DEFAULT 0
}

