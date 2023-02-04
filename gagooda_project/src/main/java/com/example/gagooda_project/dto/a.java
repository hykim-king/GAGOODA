package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
class OptionProductDto {
    private int oprionId; //옵션상품 ID
    private String productId; //상품 ID
    private int addition; //추가 상춤 여부 플래그
    private String name; //옵션 상품 이름
    private int price; //상품 가격
    private int stock; //총 수량
    private List<OptionProductDto> refundList;
}
@Data
class CommonCodeDto {
    private String mstCode; //마스터 코드
    private String detCode; //상세코드
    private String mstName; //마스터코드명
    private String detName; //상세코드명
    private int seq; //순서
    private int useYn; //사용여부
    private Date regDt; //등록일
    private String regId; //등록자
    private Date modDt; //수정일
    private String modId; //수정자
    private List<CommonCodeDto> refundList;
}