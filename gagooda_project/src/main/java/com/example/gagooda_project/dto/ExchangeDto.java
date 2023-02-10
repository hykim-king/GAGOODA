package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ExchangeDto {
    private int exchangeId;// exchange_id 교환 ID, PK, NN
    private int userId;// user_id 사용자 ID, FK, NN, exchange:user = N:1
    private int orderId;// order_id 주문 ID FK
    private int orderDetailId;// 주문 상세 ID, FK, NN exchange:order_detail = 1:1
    private int addressId; // 배송지 ID FK
    private Date modDate; // 교환 상태 변경 일자 TIMESTAMP
    private String reason; // 교환 사유, NN
    private String comment; // 교환 응답 사유
    private int count; // 교환 수량 NN
    private String imgCode; // img_code 이미지 코드
    private String exDet; // 교환처리상태_DET, FK, NN, exchange:common_code = N:1
    private String rfrDet; // 교환 사유_DET, FK, NN, exchange:common_code = N:1
    private Date date; // 교환 요청 일자 TIMESTAMP NN
}
