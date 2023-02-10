package com.example.gagooda_project.dto;

import lombok.Data;

@Data
public class CartDto {
    private int cart_id;        // 장바구니 ID
    private int option_id;      // 옵션상품 ID
    private int cart_cnt;       // 장바구니 상품 갯수
    private int user_id;        // 사용자 ID
}
