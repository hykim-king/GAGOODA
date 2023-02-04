package com.example.gagooda_project.dto;

import java.util.Date;

public class ProductDto {
    private String productId; // product_id PK NN
    private String pMst; // p_mst FK NN (COMMON_CODE : PRODUCT) 1 : N
    private String pDet; // p_det FK NN (COMMON_CODE : PRODUCT) 1 : N
    private String name; // NN
    private String place; //NN
    private int views; //NN DEFAULT 0
    private int price; //삭제 NN
    private int deliveryPc; //delivery_pc NN
    private int supplyPc; //supply_pc NN
    private int salesPc; //sales_pc NN
    private int rot; // NN
    private int margin; //NN
    private String imgCode; //img_node NN
    private Date regDate; //reg_date NN
    private String regId; //reg_id NN
    private Date modDate; //mod_date NN
    private String modId; //mod_id NN
}
