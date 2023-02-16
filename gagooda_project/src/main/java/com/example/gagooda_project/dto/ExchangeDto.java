package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ExchangeDto {
    private int exchangeId;         // exchange_id 교환 ID, PK, NN
    private int userId;             //  유저 ID, user_id, !FK, NN, ( EXCHANGE : USER ) 1 : N
    private String uname;            // 유저 이름, user.name NN
    private String email;           // 유저 이메일, user.email, NN
    private String phone;           // 유저 전화번호, user.phone, NN
    private String orderId;            // order_id 주문 ID FK
    private int orderDetailId;      // 주문 상세 ID, !FK, NN exchange:order_detail = 1:1
    private int addressId;          // 배송지 ID FK
    private int cnt;              // 교환 수량 NN
    private String reason;          // 교환 사유, NN
    private String reply;         // 교환 응답 사유
    private Date regDate;           // 교환 요청 일자 TIMESTAMP NN
    private Date modDate;           // 교환 상태 변경 일자 TIMESTAMP
    private String postCode;        // post_code 우편주소 NN
    private String address;         // 주소 NN
    private String addressDetail;   // address_detail 상세 주소 NN
    private String receiverName;    // receiver_name 수령자 이름 NN
    private String receiverPhone;   // receiver_phone 수령자 전화번호 NN
    private boolean elevator;       // 엘리베이터 유무 NN
    private String imgCode;         // img_code 이미지 코드
    private String exDet;           // 교환처리상태_DET, !FK, NN, exchange:common_code = N:1
    private String rfrDet;          // 교환 사유_DET, !FK, NN, exchange:common_code = N:1
    private OrderDto orderDto;
}
