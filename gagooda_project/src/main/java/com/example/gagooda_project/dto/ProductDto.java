package com.example.gagooda_project.dto;

import java.util.Date;

public class ProductDto {
    private String productId; // 상품 ID product_id PK NN
    private String name; // 상품명 NN
    private String place; // 판매처 NN
    private int deliveryPc; // 배송비 delivery_pc NN
    private int supplyPc; // 공급가 supply_pc NN
    private int salesPc; // 판매가 sales_pc NN
    private float rot; // 과세율 NN
    private float margin; // 마진율 NN
    private String imgCode; // 이미지 코드 img_node NN
    private String infoImgCode; // 상품 상세 정보 이미지 info_img_code NN
    private String pDet; // 상품 상태_det p_det NN
    private Date regDate; // 등록일 reg_date NN
    private int regId; // 등록자 reg_id NN
    private Date modDate; // 수정일 mod_date NN
    private int modId; // 수정자 mod_id NN
}
