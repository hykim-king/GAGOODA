package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.List;

@Data
class OptionProductDto {
    private int optionId; //옵션상품 ID
    private String productId; //상품 ID
    private int addition; //추가 상춤 여부 플래그
    private String name; //옵션 상품 이름
    private int price; //상품 가격
    private int stock; //총 수량
    private List<OptionProductDto> refundList;
}

