package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProductDto {
    private String productCode; // 상품 ID product_code PK NN
    private String pname; // 상품명 NN
    private String place; // 판매처 NN
    private int deliveryPc; // 배송비 delivery_pc NN
    private int supplyPc; // 공급가 supply_pc NN
    private int salesPc; // 판매가 sales_pc NN
    private double rot; // 과세율 NN
    private double margin; // 마진율 NN
    private String imgCode; // 이미지 코드 img_code NN
    private String infoImgCode; // 상품 상세 정보 이미지 info_img_code NN
    private String pDet; // 상품 상태_det p_det NN
    private Date regDate; // 등록일 reg_date NN
    private int regId; // 등록자 reg_id NN
    private Date modDate; // 수정일 mod_date NN
    private int modId; // 수정자 mod_id NN

    private double avgRate; // 상품 관련 평균 평점
    private int reviewCnt; // 상품 관련 평균
    private int orderCnt; // 상품 주문 수
    private List<ImageDto> imageList;
    private List<ImageDto> infoImageList;
    private List<OptionProductDto> optionProductList;
    private List<CategoryConnDto> categoryConnList;
    private List<OptionProductDto> optionUpdateList;
}
