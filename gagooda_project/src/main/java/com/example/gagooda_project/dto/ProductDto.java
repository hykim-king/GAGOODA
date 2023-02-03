package com.example.gagooda_project.dto;

import java.util.Date;

public class ProductDto {
    String productId; // product_id PK NN
    String pMst; // p_mst FK NN (COMMON_CODE : PRODUCT) 1 : N
    String pDet; // p_det FK NN (COMMON_CODE : PRODUCT) 1 : N
    String name; // NN
    String place; //NN?
    int views; //NN? DEFAULT 0?
    int price; //삭제? NN?
    int deliveryPc; //delivery_pc? NN?
    int supplyPc; //supply_pc? NN?
    int salesPc; //sales_pc? NN?
    int rot; // NN?
    int margin; //NN?
    String imgCode; //img_node? NN? 삭제?
    Date regDate; //reg_date NN?
    String regId; //reg_id NN?
    Date modDate; //mod_date NN?
    String modId; //mod_id NN?
}
