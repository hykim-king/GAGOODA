package com.example.gagooda_project.dto;

import lombok.Data;

@Data
public class CartDto {
    private int cart_id;        // 장바구니 ID PK NN AUTO_INCREMENT (COMMENT '장바구니 ID', -- 장바구니 ID)
    private int option_id;      // 옵션상품 ID FK NN (COMMENT '옵션 상품 ID', -- 옵션 상품 ID)
    private int user_id;        // 사용자 ID FK NN (COMMENT '사용자 ID' -- 사용자 ID)
}
